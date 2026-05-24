package com.sistema_escolar.sistema.escolar.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DocenteRequestDTO {

//    @NotBlank(message = "campo obrigatório!")
//    private String registroInterno;

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

    @NotBlank(message = "campo obrigatório!")
    private String formacao;

    @NotNull(message = "campo obrigatório!")
    private BigDecimal salario;

    @NotNull(message = "campo obrigatório!")
    private Long departamentoId;

    @NotNull(message = "campo obrigatório!")
    private Long usuarioId;
}
