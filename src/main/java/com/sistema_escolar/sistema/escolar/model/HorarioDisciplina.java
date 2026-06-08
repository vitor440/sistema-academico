package com.sistema_escolar.sistema.escolar.model;

import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "horario_disciplina")
@ToString(exclude = "disciplina")
@EntityListeners(AuditingEntityListener.class)
public class HorarioDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "horario")
    private LocalTime horario;

    @Column(name = "dia_semana")
    @Enumerated(EnumType.STRING)
    private DiasSemana diaSemana;

    @Column(name = "periodo")
    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @JoinColumn(name = "disciplina_id")
    @ManyToOne
    private Disciplina disciplina;
}
