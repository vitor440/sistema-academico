package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class HorarioDisciplinaRequestDTO {

    @NotNull(message = "campo obrigatório!")
    private LocalTime horario;

    @NotNull(message = "campo obrigatório!")
    private DiasSemana diaSemana;

    private Periodo periodo;
}
