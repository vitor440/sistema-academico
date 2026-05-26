package com.sistema_escolar.sistema.escolar.model;

import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resultado")
@EntityListeners(AuditingEntityListener.class)
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "aluno_id")
    @ManyToOne
    private Aluno aluno;

    @JoinColumn(name = "exame_id")
    @OneToOne
    private Exame exame;

    @Column(name = "nota")
    private Double nota;

    @JoinColumn(name = "aluno_disciplina_id")
    @ManyToOne
    private AlunoDisciplina alunoDisciplina;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;
}
