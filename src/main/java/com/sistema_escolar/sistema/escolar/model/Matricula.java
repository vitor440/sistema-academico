package com.sistema_escolar.sistema.escolar.model;

import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
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
@Table(name = "matricula")
@ToString(exclude = {"resultados", "disciplina", "aluno"})
@EntityListeners(AuditingEntityListener.class)
public class Matricula {

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

    @Column(name = "nota_final")
    private double notaFinal;

    @Column(name = "media_final")
    private double mediaFinal;

    @Column(name = "status_solicitacao")
    @Enumerated(EnumType.STRING)
    private StatusSolicitacao statusSolicitacao;

    @Column(name = "efetivado")
    private boolean efetivado;


    @Column(name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "matricula", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultado> resultados;




    public void calculaMedia(List<Resultado> resultados) {
        if(resultados == null || resultados.isEmpty()) {
            this.media = 0.0;
            calculaMediaFinal(this.media);
            return;
        }

        Double soma = 0.0;
        int somaPesos = 0;

        for (Resultado resultado : resultados) {
            soma += resultado.getNota() * resultado.getExame().getPeso();
            somaPesos += resultado.getExame().getPeso();
        }

        this.media = soma/somaPesos;
        calculaMediaFinal(this.media);
    }


    public void calculaMediaFinal(Double notaFinal) {
        this.mediaFinal = ((2*this.media) + notaFinal) / 3;
    }



    public void addResultado(Resultado resultado) {
        if(this.getResultados() == null) this.setResultados(List.of());

        this.getResultados().add(resultado);
        calculaMedia(this.resultados);
    }


    public void efetivar() {
        if (this.faltas > 7) {
            this.status = StatusDisciplina.REPROVADO_POR_FALTA;
        }

        if (this.mediaFinal >= 5) {
            this.status = StatusDisciplina.APROVADO;
        }
        else {
            this.status = StatusDisciplina.REPROVADO_POR_NOTA;
        }

        this.efetivado = true;
    }
}
