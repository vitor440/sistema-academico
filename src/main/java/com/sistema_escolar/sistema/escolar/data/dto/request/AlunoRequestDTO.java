package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
public class AlunoRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 10, max = 30, message = "matrícula deve ter entre 10 à 30 dígitos")
    private String matricula;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 50, message = "cpf deve ter entre 4 à 50 dígitos")
    @CPF(message = "CPF inváĺido!")
    private String cpf;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 300, message = "nome deve ter entre 4 à 300 dígitos")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 300, message = "email deve ter entre 4 à 300 dígitos")
    private String email;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 8, max = 80, message = "telefone deve ter entre 8 à 80 dígitos")
    private String telefone;

    @NotNull(message = "campo obrigatório!")
    @Past(message = "data de nascimento deve ser no passado!")
    private LocalDate dataNascimento;

    @NotNull(message = "campo obrigatório!")
    private Long cursoId;

    @NotNull(message = "campo obrigatório!")
    private Long usuarioId;
}
