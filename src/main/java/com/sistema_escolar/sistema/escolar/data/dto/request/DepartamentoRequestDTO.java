package com.sistema_escolar.sistema.escolar.data.dto.request;

import com.sistema_escolar.sistema.escolar.model.Docente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DepartamentoRequestDTO {

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 100, message = "nome do departamento deve ter entre 10 à 100 dígitos")
    private String nome;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 4, max = 60, message = "bloco deve ter entre 10 à 60 dígitos")
    private String bloco;

    @NotBlank(message = "campo obrigatório!")
    @Size(min = 3, max = 40, message = "sigla deve ter entre 10 à 40 dígitos")
    private String sigla;
}
