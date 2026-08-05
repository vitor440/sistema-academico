package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PermissionRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 2, max = 100, message = "role deve ter entre 10 à 300 dígitos")
    private String role;

}
