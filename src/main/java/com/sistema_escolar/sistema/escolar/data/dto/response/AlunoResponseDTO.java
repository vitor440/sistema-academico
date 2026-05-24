package com.sistema_escolar.sistema.escolar.data.dto.response;

import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AlunoResponseDTO {

    private Long id;

    private String matricula;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private Integer cursoPeriodo;

    private Long cursoId;

    private Long usuarioId;

}
