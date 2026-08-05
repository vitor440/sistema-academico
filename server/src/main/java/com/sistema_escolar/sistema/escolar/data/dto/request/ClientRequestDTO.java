package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 300, message = "clientId deve ter entre 10 a 300 dígitos!")
    private String clientId;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 500, message = "clientSecret deve ter entre 4 a 500 dígitos!")
    private String clientSecret;


}
