package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlunoRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    private String matricula;

    @NotBlank(message = "campo obrigatório!")
    private String cpf;

    @NotBlank(message = "campo obrigatório!")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    private String email;

    @NotBlank(message = "campo obrigatório!")
    private String telefone;

    @NotNull(message = "campo obrigatório!")
    private LocalDate dataNascimento;

    @NotNull(message = "campo obrigatório!")
    private Integer cursoPeriodo;

    @NotNull(message = "campo obrigatório!")
    private Long cursoId;

    @NotNull(message = "campo obrigatório!")
    private Long usuarioId;
}
