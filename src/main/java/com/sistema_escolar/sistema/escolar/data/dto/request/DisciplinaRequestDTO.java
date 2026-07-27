package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class DisciplinaRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 100, message = "nome da disciplina deve ter entre 10 à 100 dígitos!")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 60, message = "localizacao deve ter entre 10 à 60 dígitos!")
    private String localizacao;

    @NotNull(message = "campo obrigatório!")
    @PositiveOrZero
    private Integer alunosMatriculados;

    @NotNull(message = "campo obrigatório!")
    @PositiveOrZero
    private Integer vagas;

    @NotNull(message = "campo obrigatório!")
    private Long departamentoId;

    @NotNull(message = "campo obrigatório!")
    private Long docenteId;

    private List<HorarioDisciplinaRequestDTO> horarios;
}
