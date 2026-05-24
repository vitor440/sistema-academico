package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.DocenteMapper;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.DepartamentoRepository;
import com.sistema_escolar.sistema.escolar.repository.DocenteRepository;
import com.sistema_escolar.sistema.escolar.repository.UsuarioRepository;
import com.sistema_escolar.sistema.escolar.service.DocenteService;
import com.sistema_escolar.sistema.escolar.validator.DocenteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository repository;
    private final DepartamentoRepository departamentoRepository;
    private final DocenteMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final DocenteValidator validator;

    @Override
    public DocenteResponseDTO salvar(DocenteRequestDTO requestDTO) {
        Docente docente = mapper.toEntity(requestDTO);
        Departamento departamento = getDepartamento(requestDTO.getDepartamentoId());
        docente.setRegistroInterno(UUID.randomUUID().toString().substring(10));
        docente.setDepartamento(departamento);
        docente.setUsuario(getUsuario(requestDTO.getUsuarioId()));

        validator.validar(docente);
        return mapper.toDTO(repository.save(docente));
    }

    @Override
    public DocenteResponseDTO atualizar(Long id, DocenteRequestDTO requestDTO) {
        Docente docente = getDocente(id);
        docente.setCpf(requestDTO.getCpf());
        docente.setNome(requestDTO.getNome());
        docente.setEmail(requestDTO.getEmail());
        docente.setTelefone(requestDTO.getTelefone());
        docente.setFormacao(requestDTO.getFormacao());
        docente.setDataNascimento(requestDTO.getDataNascimento());
        docente.setSalario(requestDTO.getSalario());
        docente.setDepartamento(getDepartamento(requestDTO.getDepartamentoId()));
        docente.setUsuario(getUsuario(requestDTO.getUsuarioId()));

        validator.validar(docente);
        return mapper.toDTO(repository.save(docente));
    }

    @Override
    public DocenteResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getDocente(id));
    }

    @Override
    public Page<DocenteResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Docente docente = getDocente(id);
        repository.delete(docente);
    }

    private Docente getDocente(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Docente não encontrado!"));
    }

    private Departamento getDepartamento(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Departamento não encontrado!"));
    }

    private Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuario não encontrado!"));
    }
}
