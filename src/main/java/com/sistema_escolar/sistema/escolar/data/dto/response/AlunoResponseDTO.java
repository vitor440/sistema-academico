package com.sistema_escolar.sistema.escolar.data.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlunoResponseDTO {

    private Long id;

    private String matricula;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private Long cursoId;

    private Long usuarioId;

}
