package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class PermissionRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 10, max = 100, message = "role deve ter entre 10 à 300 dígitos")
    private String role;

}
