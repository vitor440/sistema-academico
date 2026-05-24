package com.sistema_escolar.sistema.escolar.model;

import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "curso")
@EntityListeners(AuditingEntityListener.class)
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    private Areas area;

    @Column(name = "quantidade_alunos")
    private int quantidadeAlunos;

    @Column(name = "periodo")
    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @Column(name = "quantidade_periodos")
    private int quantidadePeriodos;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "curso")
    private List<Aluno> alunos;
}