package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DocenteRequestDTO {

//    @NotBlank(message = "campo obrigatório!")
//    private String registroInterno;

    @NotBlank(message = "campo obrigatório!")
    @CPF(message = "CPF inváĺido!")
    private String cpf;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 300, message = "nome deve ter entre 10 à 300 dígitos")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 300, message = "email deve ter entre 10 à 300 dígitos")
    private String email;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 8, max = 300, message = "telefone deve ter entre 8 à 80 dígitos")
    private String telefone;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 80, message = "senha deve ter entre 4 à 80 dígitos")
    private String senha;

    @NotNull(message = "campo obrigatório!")
    @Past
    private LocalDate dataNascimento;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 10, max = 300, message = "formação deve ter entre 30 à 600 dígitos")
    private String formacao;

    @NotNull(message = "campo obrigatório!")
    @Digits(integer = 8, fraction = 2, message = "salário com formato incorreto!")
    private BigDecimal salario;

    @NotNull(message = "campo obrigatório!")
    private Long departamentoId;
}
