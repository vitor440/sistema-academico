package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.PermissionRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.PermissionResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:50-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toEntity(PermissionRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setRole( requestDTO.getRole() );

        return permission;
    }

    @Override
    public PermissionResponseDTO toDTO(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponseDTO permissionResponseDTO = new PermissionResponseDTO();

        permissionResponseDTO.setId( permission.getId() );
        permissionResponseDTO.setRole( permission.getRole() );

        return permissionResponseDTO;
    }
}
