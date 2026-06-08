package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResultadoRequestDTO {

    @NotNull(message = "campo obrigatório!")
    private Long exameId;

    @NotNull(message = "campo obrigatório!")
    @DecimalMin(value = "0", message = "A nota deve estar entre 0 e 10!")
    @DecimalMax(value = "10")
    private Double nota;
}
