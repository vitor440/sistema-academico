package com.sistema_escolar.sistema.escolar.data.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DocenteResponseDTO {

    private Long id;

    private String registroInterno;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private String formacao;

    private BigDecimal salario;

    private long departamentoId;

    private long usuarioId;
}
