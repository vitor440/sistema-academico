package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExameRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 100, message = "nome do exame deve ter entre 10 à 100 dígitos")
    private String nome;

    @NotNull(message = "campo obrigatório!")
    private Long disciplinaId;

    @NotNull(message = "campo obrigatório!")
    @Future(message = "data do exame deve ser futura!")
    private LocalDate data;

    @NotNull(message = "campo obrigatório!")
    private TipoExame tipo;

    @NotNull(message = "campo obrigatório!")
    private LocalTime hora;

    @NotNull(message = "campo obrigatório!")
    @Positive(message = "peso deve ser positivo!")
    private Integer peso;
}
