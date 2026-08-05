package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.PermissionRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.PermissionResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.PermissionMapper;
import com.sistema_escolar.sistema.escolar.model.Permission;
import com.sistema_escolar.sistema.escolar.repository.PermissionRepository;
import com.sistema_escolar.sistema.escolar.service.PermissionService;
import com.sistema_escolar.sistema.escolar.validator.PermissionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionValidator validator;
    private final PermissionMapper mapper;

    @Override
    public PermissionResponseDTO salvar(PermissionRequestDTO requestDTO) {
        Permission permission = mapper.toEntity(requestDTO);

        validator.validar(permission);
        return mapper.toDTO(repository.save(permission));
    }

    @Override
    public PermissionResponseDTO atualizar(Long id, PermissionRequestDTO requestDTO) {
        Permission permission = getPermission(id);
        permission.setRole(requestDTO.getRole());

        validator.validar(permission);
        return mapper.toDTO(repository.save(permission));
    }

    @Override
    public Page<PermissionResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "role");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public PermissionResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getPermission(id));
    }

    @Override
    public void deletarPeloId(Long id) {
        Permission permission = getPermission(id);
        repository.delete(permission);
    }


    private Permission getPermission(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Permission não encontrada!"));
    }
}
