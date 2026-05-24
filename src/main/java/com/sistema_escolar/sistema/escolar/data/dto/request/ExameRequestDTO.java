package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExameRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    private String nome;

    @NotNull(message = "campo obrigatório!")
    private Long disciplinaId;

    @NotNull(message = "campo obrigatório!")
    private LocalDate data;

    @NotNull(message = "campo obrigatório!")
    private TipoExame tipo;

    @NotNull(message = "campo obrigatório!")
    private LocalTime hora;
}
