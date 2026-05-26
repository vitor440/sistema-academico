package com.sistema_escolar.sistema.escolar.model;

import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "exame")
@EntityListeners(AuditingEntityListener.class)
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JoinColumn(name = "disciplina_id")
    @ManyToOne
    private Disciplina disciplina;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoExame tipo;

    @Column(name = "peso")
    private int peso;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;
}
