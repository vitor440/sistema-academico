package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.enums.StatusExame;
import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import lombok.Data;

import java.time.LocalDate;
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

    private StatusExame status;
}
