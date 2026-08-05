package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import lombok.Data;

@Data
public class CursoResponseDTO {

    private Long id;

    private String nome;

    private Areas area;

    private int quantidadeAlunos;

    private Periodo periodo;

    private int quantidadePeriodos;

    private Long departamentoId;
}
