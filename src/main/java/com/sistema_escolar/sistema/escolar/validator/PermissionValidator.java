package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Permission;
import com.sistema_escolar.sistema.escolar.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PermissionValidator {

    private final PermissionRepository repository;

    public void validar(Permission permission) {
        if (duplicateRole(permission)) {
            throw new RegistroDuplicadoException("Role \"" + permission.getRole() + "\" Já existe");
        }
    }

    private boolean duplicateRole(Permission permission) {

        Optional<Permission> permissionOpt = repository.findByRole(permission.getRole());

        if(permission.getId() == null) {
            return permissionOpt.isPresent();
        }

        return permissionOpt.map(Permission::getId).stream().anyMatch(id -> !id.equals(permission.getId()));
    }
}
