package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import lombok.Data;

import java.time.LocalTime;

@Data
public class HorarioDisciplinaResponseDTO {

    private Long id;

    private LocalTime horario;

    private DiasSemana diaSemana;

    private Periodo periodo;

    private Long disciplinaId;


}
