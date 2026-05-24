package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DocenteResponseDTO {

    private Long id;

    private String registroInterno;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private String formacao;

    private BigDecimal salario;

    private long departamentoId;

    private long usuarioId;
}
