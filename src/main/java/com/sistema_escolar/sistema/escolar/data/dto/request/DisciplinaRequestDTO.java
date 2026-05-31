package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DisciplinaRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 10, max = 100, message = "nome da disciplina deve ter entre 10 à 100 dígitos!")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 10, max = 60, message = "localizacao deve ter entre 10 à 60 dígitos!")
    private String localizacao;

    @NotNull(message = "campo obrigatório!")
    @PositiveOrZero
    private Integer alunosMatriculados;

    @NotNull(message = "campo obrigatório!")
    @PositiveOrZero
    private Integer vagas;

    @NotNull(message = "campo obrigatório!")
    private DiasSemana diaSemana;

    @NotNull(message = "campo obrigatório!")
    private Periodo periodo;

    @NotNull(message = "campo obrigatório!")
    private LocalTime horaInicio;

    @NotNull(message = "campo obrigatório!")
    private Long departamentoId;

    @NotNull(message = "campo obrigatório!")
    private Long docenteId;
}
