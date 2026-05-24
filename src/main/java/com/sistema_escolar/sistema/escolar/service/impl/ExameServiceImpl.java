package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ExameMapper;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.validator.ExameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExameServiceImpl implements ExameService {

    private final ExameRepository repository;
    private final ExameMapper mapper;
    private final DisciplinaRepository disciplinaRepository;
    private final ExameValidator validator;

    @Override
    public ExameResponseDTO salvar(ExameRequestDTO requestDTO) {
        Exame exame = mapper.toEntity(requestDTO);
        Disciplina disciplina = getDisciplina(requestDTO.getDisciplinaId());
        exame.setDisciplina(disciplina);

        validator.validar(exame);
        return mapper.toDTO(repository.save(exame));
    }

    @Override
    public ExameResponseDTO atualizar(Long id, ExameRequestDTO requestDTO) {
        Exame exame = getExame(id);
        exame.setNome(requestDTO.getNome());
        exame.setDisciplina(getDisciplina(requestDTO.getDisciplinaId()));
        exame.setData(requestDTO.getData());
        exame.setHora(requestDTO.getHora());
        exame.setTipo(requestDTO.getTipo());

        validator.validar(exame);
        return mapper.toDTO(repository.save(exame));
    }

    @Override
    public ExameResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getExame(id));
    }

    @Override
    public Page<ExameResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Exame exame = getExame(id);
        repository.delete(exame);
    }

    private Exame getExame(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Exame não encontrado!"));
    }

    private Disciplina getDisciplina(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Disciplina não encontrada!"));
    }
}



