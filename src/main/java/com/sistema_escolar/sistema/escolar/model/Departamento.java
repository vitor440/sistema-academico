package com.sistema_escolar.sistema.escolar.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "departamento")
@EntityListeners(AuditingEntityListener.class)
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "bloco")
    private String bloco;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Docente> docentes;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Disciplina> disciplinas;
}
