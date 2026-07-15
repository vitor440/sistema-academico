package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.CursoMapper;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import com.sistema_escolar.sistema.escolar.repository.CursoRepository;
import com.sistema_escolar.sistema.escolar.repository.specs.CursoSpecs;
import com.sistema_escolar.sistema.escolar.service.CursoService;
import com.sistema_escolar.sistema.escolar.service.DepartamentoService;
import com.sistema_escolar.sistema.escolar.validator.CursoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository repository;
    private final CursoMapper mapper;
    private final CursoValidator validator;
    private final DepartamentoService departamentoService;

    @Override
    public CursoResponseDTO salvar(CursoRequestDTO requestDTO) {
        Curso curso = mapper.toEntity(requestDTO);
        Departamento departamento = departamentoService.getDepartamento(requestDTO.getDepartamentoId());
        curso.setDepartamento(departamento);

        validator.validar(curso);
        return mapper.toDTO(repository.save(curso));
    }

    @Override
    public CursoResponseDTO atualizar(Long id, CursoRequestDTO requestDTO) {
        Curso curso = getCurso(id);
        curso.setNome(requestDTO.getNome());
        curso.setArea(requestDTO.getArea());
        curso.setPeriodo(requestDTO.getPeriodo());
        curso.setQuantidadeAlunos(requestDTO.getQuantidadeAlunos());
        curso.setQuantidadePeriodos(requestDTO.getQuantidadePeriodos());
        Departamento departamento = departamentoService.getDepartamento(requestDTO.getDepartamentoId());
        curso.setDepartamento(departamento);

        validator.validar(curso);
        return mapper.toDTO(repository.save(curso));
    }

    @Override
    public CursoResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getCurso(id));
    }

    @Override
    public Page<CursoResponseDTO> listar(String nome, Areas area, Periodo periodo, Integer quantidadePeriodos,
                                         int pagina, int tamanho, String sortDirection) {
        Direction direction = sortDirection.equalsIgnoreCase("ASC")? Direction.ASC: Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        Specification<Curso> specs = ((root, query, cb) -> cb.conjunction());

        if (nome != null) specs = specs.and(CursoSpecs.findByName(nome));

        if (area != null) specs = specs.and(CursoSpecs.findByArea(area));

        if (periodo != null) specs = specs.and(CursoSpecs.findByPeriodo(periodo));

        if (quantidadePeriodos != null) specs = specs.and(CursoSpecs.findByQuantidadePeriodo(quantidadePeriodos));

        return repository.findAll(specs, pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Curso curso = getCurso(id);
        validator.validaDelecao(curso);
        repository.delete(curso);
    }

    @Override
    public Curso getCurso(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Curso não encontrado!"));
    }

    @Override
    public List<Object[]> quantidadeDeAreas() {
        return repository.quantidadeDeAreas();
    }

    @Override
    public List<Object[]> alunosPorCurso() {
        return repository.alunosPorCurso();
    }

    @Override
    public Long countCurso() {
        return repository.count();
    }
}

