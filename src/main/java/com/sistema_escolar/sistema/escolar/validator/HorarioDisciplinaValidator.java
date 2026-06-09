package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.HorarioDisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HorarioDisciplinaValidator {

    private final List<LocalTime> HORARIOS = List.of(LocalTime.of(8, 0), LocalTime.of(10, 0),
            LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0), LocalTime.of(20, 0));

    private final HorarioDisciplinaRepository horarioDisciplinaRepository;


    public void validar(HorarioDisciplina horarioDisciplina) {
        if (verificaDuplicacao(horarioDisciplina)) {
            throw new RegistroDuplicadoException("Já existe um horário para essa disciplina neste dia da semana e horário!");
        }

        if (HORARIOS.contains(horarioDisciplina.getHorario())) {
            throw new RegistroConflitanteException("O horário da disciplina deve estar contido: 08:00, 10:00, 14:00, 16:00, 18:00 e 20:00");
        }

    }

    private boolean verificaDuplicacao(HorarioDisciplina horarioDisciplina) {
        Optional<HorarioDisciplina> horarioDisciplinaOpt = horarioDisciplinaRepository
                .findByHorarioAndDiaSemanaAndDisciplina(horarioDisciplina.getHorario(), horarioDisciplina.getDiaSemana(),
                        horarioDisciplina.getDisciplina());

        if (horarioDisciplina.getId() == null) {
            return horarioDisciplinaOpt.isPresent();
        }

        return horarioDisciplinaOpt.map(HorarioDisciplina::getId).stream().anyMatch(id -> !id.equals(horarioDisciplina.getId()));
    }

}
