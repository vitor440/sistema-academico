package com.sistema_escolar.sistema.escolar.model;

import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "aluno_disciplina")
@EntityListeners(AuditingEntityListener.class)
public class AlunoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "aluno_id")
    @ManyToOne
    private Aluno aluno;

    @JoinColumn(name = "disciplina_id")
    @ManyToOne
    private Disciplina disciplina;

    @Column(name = "faltas")
    private int faltas;

    @Column(name = "media")
    private Double media;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusDisciplina status;

    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "alunoDisciplina", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultado> resultados;


    public void calculaMedia(List<Resultado> resultados) {

        if (resultados == null || resultados.size() == 0){
            this.media = 0.0;
            return;
        }

        Double soma = 0.0;
        int somaPesos = 0;

        for (Resultado resultado : resultados) {
            soma += resultado.getNota() * resultado.getExame().getPeso();
            somaPesos += resultado.getExame().getPeso();
        }

        this.media = soma/somaPesos;
    }

    public void addResultado(Resultado resultado) {
        if(this.getResultados() == null) this.setResultados(List.of());

        this.getResultados().add(resultado);
        calculaMedia(this.resultados);
    }
}
