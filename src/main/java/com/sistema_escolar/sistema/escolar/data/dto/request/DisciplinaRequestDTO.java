package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DisciplinaRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    private String localizacao;

    @NotNull(message = "campo obrigatório!")
    private Integer alunosMatriculados;

    @NotNull(message = "campo obrigatório!")
    private Integer vagas;

    @NotNull(message = "campo obrigatório!")
    private DiasSemana diaSemana;

    @NotNull(message = "campo obrigatório!")
    private Periodo periodo;

    @NotNull(message = "campo obrigatório!")
    private LocalTime horaInicio;

//    @NotNull(message = "campo obrigatório!")
//    private LocalTime horaFim;

    @NotNull(message = "campo obrigatório!")
    private Long departamentoId;

    @NotNull(message = "campo obrigatório!")
    private Long docenteId;
}
