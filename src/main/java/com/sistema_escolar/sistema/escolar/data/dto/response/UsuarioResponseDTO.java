package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Permission;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UsuarioResponseDTO {

    private Long id;

    private String username;

    private String email;

    private List<PermissionResponseDTO> permissions;

}
