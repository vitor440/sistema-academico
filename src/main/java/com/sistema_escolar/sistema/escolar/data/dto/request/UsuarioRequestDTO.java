package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 100)
    private String username;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 8, max = 100)
    private String email;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 8, max = 50)
    private String senha;

}
