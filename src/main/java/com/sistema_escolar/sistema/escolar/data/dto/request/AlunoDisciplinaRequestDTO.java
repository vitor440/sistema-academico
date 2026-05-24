package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AlunoDisciplinaRequestDTO {

    @NotNull(message = "campo obrigatório!")
    private Long alunoId;

    @NotNull(message = "campo obrigatório!")
    private Long disciplinaId;

    @NotNull(message = "campo obrigatório!")
    private Integer faltas;

    @NotNull(message = "campo obrigatório!")
    private StatusDisciplina status;
}
