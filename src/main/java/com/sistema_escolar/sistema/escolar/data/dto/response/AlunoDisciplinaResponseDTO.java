package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AlunoDisciplinaResponseDTO {

    private Long id;

    private Long alunoId;

    private Long disciplinaId;

    private Integer faltas;

    private Double media;

    private StatusDisciplina status;

    private List<ResultadoResponseDTO> resultados;
}
