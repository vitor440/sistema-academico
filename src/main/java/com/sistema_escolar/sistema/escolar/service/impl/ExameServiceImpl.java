package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ExameMapper;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.validator.ExameValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExameServiceImpl implements ExameService {

    private final ExameRepository repository;
    private final ExameMapper mapper;
    private final DisciplinaService disciplinaService;
    private final ExameValidator validator;
    private final MatriculaRepository matriculaRepository;

    @Override
    public ExameResponseDTO salvar(ExameRequestDTO requestDTO) {
        Exame exame = mapper.toEntity(requestDTO);
        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());
        exame.setDisciplina(disciplina);

        validator.validar(exame);
        return mapper.toDTO(repository.save(exame));
    }

    @Override
    public ExameResponseDTO atualizar(Long id, ExameRequestDTO requestDTO) {
        Exame exame = getExame(id);
        exame.setNome(requestDTO.getNome());
        exame.setDisciplina(disciplinaService.getDisciplina(requestDTO.getDisciplinaId()));
        exame.setData(requestDTO.getData());
        exame.setHora(requestDTO.getHora());
        exame.setTipo(requestDTO.getTipo());
        exame.setPeso(requestDTO.getPeso());

        validator.validar(exame);
        return mapper.toDTO(repository.save(exame));
    }

    @Override
    public ExameResponseDTO obterPeloId(Long id) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Exame exame = getExame(id);

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            boolean ehMatriculado = matriculaRepository.existsByAlunoAndDisciplina(usuarioLogado.getAluno(), exame.getDisciplina());

            if (!ehMatriculado) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para ver esse exame!");
            }
        }
        return mapper.toDTO(exame);
    }

    @Override
    @Transactional
    public Page<ExameResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioLogado.getRoles().contains("ALUNO")) {

            List<Exame> exames = repository.findAll();

            List<Exame> examesFiltrados = exames
                    .stream()
                    .filter(exame -> usuarioLogado.getAluno()
                            .getDisciplinas()
                            .contains(exame.getDisciplina()))
                    .toList();

            return new PageImpl<>(examesFiltrados, pageable, examesFiltrados.size()).map(mapper::toDTO);
        }

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Exame exame = getExame(id);
        repository.delete(exame);
    }

    @Override
    public Exame getExame(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Exame não encontrado!"));
    }
}



