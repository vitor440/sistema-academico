package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ExameMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.ExameValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExameServiceImpl implements ExameService {

    private final ExameRepository repository;
    private final ExameMapper mapper;
    private final DisciplinaService disciplinaService;
    private final ExameValidator validator;
    private final UsuarioService usuarioService;

    @Override
    public ExameResponseDTO salvar(ExameRequestDTO requestDTO) {
        Exame exame = mapper.toEntity(requestDTO);
        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());
        Docente docente = disciplina.getDocente();
        validator.validarDocenteLogado(docente);
        exame.setDisciplina(disciplina);

        validator.validar(exame);
        return mapper.toDTO(repository.save(exame));
    }

    @Override
    public ExameResponseDTO atualizar(Long id, ExameRequestDTO requestDTO) {
        Exame exame = getExame(id);

        Docente docente = exame.getDisciplina().getDocente();
        validator.validarDocenteLogado(docente);

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
        Exame exame = getExame(id);
        validator.validarAcesso(exame);
        return mapper.toDTO(exame);
    }

    @Override
    @Transactional
    public Page<ExameResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioLogado.getAluno();
            return repository.obterExamesDeAluno(aluno, pageable).map(mapper::toDTO);
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docente = usuarioLogado.getDocente();
            return repository.obterExamesDoDocente(docente, pageable).map(mapper::toDTO);
        }
        else {
            return repository.findAll(pageable).map(mapper::toDTO);
        }

    }

    @Override
    public void deletarPeloId(Long id) {
        Exame exame = getExame(id);

        Docente docente = exame.getDisciplina().getDocente();
        validator.validarDocenteLogado(docente);

        repository.delete(exame);
    }

    @Override
    public Exame getExame(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Exame não encontrado!"));
    }
}



