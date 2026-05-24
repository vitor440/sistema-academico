package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.Docente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DepartamentoRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    private String bloco;

    @NotBlank(message = "campo obrigatório!")
    private String sigla;
}
