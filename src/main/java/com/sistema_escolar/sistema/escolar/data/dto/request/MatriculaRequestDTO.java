package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MatriculaRequestDTO {

    @NotNull(message = "campo obrigatório!")
    private Long alunoId;

    @NotNull(message = "campo obrigatório!")
    private Long disciplinaId;

    @NotNull(message = "campo obrigatório!")
    @PositiveOrZero
    private Integer faltas;
}
