package com.sistema_escolar.sistema.escolar.data.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponseDTO {

    private Long id;

    private String username;

    private String email;

    private List<PermissionResponseDTO> permissions;

}
