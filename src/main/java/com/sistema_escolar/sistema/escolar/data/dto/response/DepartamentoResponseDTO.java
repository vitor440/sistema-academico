package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Docente;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DepartamentoResponseDTO {

    private Long id;

    private String nome;

    private String bloco;

    private String sigla;

}
