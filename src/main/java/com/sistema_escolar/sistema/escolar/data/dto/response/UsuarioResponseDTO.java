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

    private String senha;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean enabled;

    private List<Permission> permissions;

}
