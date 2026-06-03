package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MatriculaResponseDTO {

    private Long id;

    private Long alunoId;

    private Long disciplinaId;

    private Integer faltas;

    private Double media;

    private StatusDisciplina status;

    private Double notaFinal;

    private Double mediaFinal;

    private StatusSolicitacao statusSolicitacao;

    private boolean efetivado;

    private List<ResultadoResponseDTO> resultados;
}
