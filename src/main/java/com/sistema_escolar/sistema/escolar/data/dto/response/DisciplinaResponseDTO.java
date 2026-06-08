package com.sistema_escolar.sistema.escolar.data.dto.response;

import lombok.Data;

@Data
public class DisciplinaResponseDTO {

    private Long id;

    private String nome;

    private String localizacao;

    private Integer alunosMatriculados;

    private Integer vagas;

    private Long departamentoId;

    private Long docenteId;
}
