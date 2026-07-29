package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 5, max = 300, message = "nome do curso deve ter entre 5 a 300 dígitos!")
    private String nome;

    @NotNull(message = "campo obrigatório!")
    private Areas area;

//    @NotNull(message = "campo obrigatório!")
//    @PositiveOrZero
//    private int quantidadeAlunos;

    @NotNull(message = "campo obrigatório!")
    private Periodo periodo;

    @NotNull(message = "campo obrigatório!")
    @Min(value = 4, message = "quantidade de periodos deve estar entre 4 e 12!")
    @Max(value = 12, message = "quantidade de periodos deve estar entre 4 e 12!")
    private int quantidadePeriodos;

    @NotNull(message = "campo obrigatório!")
    private Long departamentoId;
}

