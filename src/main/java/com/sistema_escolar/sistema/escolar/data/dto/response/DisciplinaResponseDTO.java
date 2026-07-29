package com.sistema_escolar.sistema.escolar.data.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DisciplinaResponseDTO {

    private Long id;

    private String nome;

    private String localizacao;

    private Integer alunosMatriculados;

    private Integer vagas;

    private Long departamentoId;

    private Long docenteId;

    private String departamento;

    private String docente;

    private List<HorarioDisciplinaResponseDTO> horarios;
}
