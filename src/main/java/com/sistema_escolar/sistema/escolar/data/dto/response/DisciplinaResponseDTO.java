package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class DisciplinaResponseDTO {

    private Long id;

    private String nome;

    private String localizacao;

    private Integer alunosMatriculados;

    private Integer vagas;

    private DiasSemana diaSemana;

    private Periodo periodo;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private Long departamentoId;

    private Long docenteId;
}
