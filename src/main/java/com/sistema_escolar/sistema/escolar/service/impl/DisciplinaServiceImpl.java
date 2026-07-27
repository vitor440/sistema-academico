package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.DisciplinaMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.specs.DisciplinaSpecs;
import com.sistema_escolar.sistema.escolar.service.DepartamentoService;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.DocenteService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.DisciplinaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository repository;
    private final DisciplinaMapper mapper;
    private final DocenteService docenteService;
    private final DepartamentoService departamentoService;
    private final DisciplinaValidator validator;

    @Override
    public DisciplinaResponseDTO salvar(DisciplinaRequestDTO requestDTO) {
        Disciplina disciplina = mapper.toEntity(requestDTO);
        Departamento departamento = departamentoService.getDepartamento(requestDTO.getDepartamentoId());
        Docente docente = docenteService.getDocente(requestDTO.getDocenteId());
        disciplina.setDepartamento(departamento);
        disciplina.setDocente(docente);

        if(disciplina.getHorarios() != null && !disciplina.getHorarios().isEmpty()) {
            for (HorarioDisciplina horario : disciplina.getHorarios()) {
                horario.setDisciplina(disciplina);
            }
        }

        validator.validar(disciplina);
        return mapper.toDTO(repository.save(disciplina));
    }

    @Override
    public DisciplinaResponseDTO atualizar(Long id, DisciplinaRequestDTO requestDTO) {
        Disciplina disciplina = getDisciplina(id);

        disciplina.setNome(requestDTO.getNome());
        disciplina.setLocalizacao(requestDTO.getLocalizacao());
        disciplina.setAlunosMatriculados(requestDTO.getAlunosMatriculados());
        disciplina.setVagas(requestDTO.getVagas());
        disciplina.setDepartamento(departamentoService.getDepartamento(requestDTO.getDepartamentoId()));
        disciplina.setDocente(docenteService.getDocente(requestDTO.getDocenteId()));

        validator.validar(disciplina);
        return mapper.toDTO(repository.save(disciplina));
    }

    @Override
    public DisciplinaResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getDisciplina(id));
    }

    @Override
    public Page<DisciplinaResponseDTO> listar(int pagina, int tamanho, String sortDirection, String nome, Long docenteId, Integer semestre, Integer ano) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "alunosMatriculados");
        Specification<Disciplina> specs = (root, query, cb) -> cb.conjunction();

        if (nome != null) specs = specs.and(DisciplinaSpecs.findByNome(nome));
        if (docenteId != null) specs = specs.and(DisciplinaSpecs.findByDocenteId(docenteId));
        if (semestre != null) specs = specs.and(DisciplinaSpecs.findBySemestre(semestre));
        if (ano != null) specs = specs.and(DisciplinaSpecs.findByAno(ano));

        return repository.findAll(specs, pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Disciplina disciplina = getDisciplina(id);
        validator.validaDelecao(disciplina);
        repository.delete(disciplina);
    }

    @Override
    public Disciplina getDisciplina(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Disciplina não encontrada!"));
    }
}
