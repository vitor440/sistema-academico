package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.DepartamentoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.CursoMapper;
import com.sistema_escolar.sistema.escolar.mapper.DepartamentoMapper;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.repository.DepartamentoRepository;
import com.sistema_escolar.sistema.escolar.service.DepartamentoService;
import com.sistema_escolar.sistema.escolar.validator.DepartamentoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository repository;
    private final DepartamentoMapper mapper;
    private final DepartamentoValidator validator;
    private final CursoMapper cursoMapper;

    @Override
    public DepartamentoResponseDTO salvar(DepartamentoRequestDTO requestDTO) {
        Departamento departamento = mapper.toEntity(requestDTO);

        validator.validar(departamento);
        return mapper.toDTO(repository.save(departamento));
    }

    @Override
    public DepartamentoResponseDTO atualizar(Long id, DepartamentoRequestDTO requestDTO) {
        Departamento departamento = getDepartamento(id);
        departamento.setNome(requestDTO.getNome());
        departamento.setBloco(requestDTO.getBloco());
        departamento.setSigla(requestDTO.getSigla());

        validator.validar(departamento);
        return mapper.toDTO(repository.save(departamento));
    }

    @Override
    public DepartamentoResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getDepartamento(id));
    }

    @Override
    public Page<DepartamentoResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Direction direction = sortDirection.equalsIgnoreCase("ASC")? Direction.ASC: Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Departamento departamento = getDepartamento(id);
        repository.delete(departamento);
    }

    @Override
    public Departamento getDepartamento(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Departamento não encontrado!"));
    }
}
