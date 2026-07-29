package com.sistema_escolar.sistema.escolar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "aluno")
@ToString(exclude = {"usuario", "matriculas", "curso"})
@EntityListeners(AuditingEntityListener.class)
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @JoinColumn(name = "curso_id")
    @ManyToOne
    private Curso curso;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Usuario usuario;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE)
    private List<Matricula> matriculas;



    public List<Disciplina> getDisciplinas() {
        return this.matriculas
                .stream()
                .map(matricula -> matricula.getDisciplina())
                .toList();
    }
}
