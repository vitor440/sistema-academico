package com.sistema_escolar.sistema.escolar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "disciplina")
@ToString(exclude = {"matriculas", "exames", "departamento", "docente", "horarios"})
@EntityListeners(AuditingEntityListener.class)
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "localizacao")
    private String localizacao;

    @Column(name = "alunos_matriculados")
    private int alunosMatriculados;

    @Column(name = "vagas")
    private int vagas;

    @JoinColumn(name = "departamento_id")
    @ManyToOne
    private Departamento departamento;

    @JoinColumn(name = "docente_id")
    @ManyToOne
    private Docente docente;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;


    @OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Exame> exames;

    @OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY)
    private List<Matricula> matriculas;

    @OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisciplina> horarios;


    public void decrementaVaga() {
        this.vagas -= 1;
        this.alunosMatriculados += 1;
    }

    public void acrescentaVaga() {
        this.vagas += 1;
        this.alunosMatriculados -= 1;
    }
}
