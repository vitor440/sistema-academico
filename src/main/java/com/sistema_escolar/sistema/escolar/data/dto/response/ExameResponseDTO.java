package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ExameResponseDTO {

    private Long id;

    private String nome;

    private Long disciplinaId;

    private LocalDate data;

    private LocalTime hora;

    private TipoExame tipo;

    private Integer peso;
}
