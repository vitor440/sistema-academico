package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    private String nome;

    @NotNull(message = "campo obrigatório!")
    private Areas area;

    @NotNull(message = "campo obrigatório!")
    private int quantidadeAlunos;

    @NotNull(message = "campo obrigatório!")
    private Periodo periodo;

    @NotNull(message = "campo obrigatório!")
    @Min(value = 4, message = "quantidade de periodos deve estar entre 4 e 12!")
    @Max(value = 12, message = "quantidade de periodos deve estar entre 4 e 12!")
    private int quantidadePeriodos;
}

