package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DisciplinaValidator {

    private final DisciplinaRepository repository;
    private final List<LocalTime> horariosValidos = List.of(LocalTime.of(8,0), LocalTime.of(10,0),
            LocalTime.of(14,0), LocalTime.of(16,0), LocalTime.of(18,0));

    public void validar(Disciplina disciplina) {
        if (disciplinaDuplicada(disciplina)) {
            throw new RegistroDuplicadoException("Já existe uma disciplina com nome: ' " + disciplina.getNome() + "'e pertencente ao departamento: '"
                    + disciplina.getDepartamento().getNome() + "'");
        }

        if (disciplina.getDiaSemana().equals(DiasSemana.DOMINGO) || disciplina.getDiaSemana().equals(DiasSemana.SABADO)) {
            throw new RuntimeException("");
        }

        if (!horariosValidos.contains(disciplina.getHoraInicio())) {
            throw new RegistroConflitanteException("Horário inválido!");
        }

    }

    private boolean disciplinaDuplicada(Disciplina disciplina) {
        Optional<Disciplina> disciplinaOpt = repository.findByNomeAndDepartamento(disciplina.getNome(), disciplina.getDepartamento());

        if(disciplina.getId() == null) {
            return disciplinaOpt.isPresent();
        }

        return disciplinaOpt.map(Disciplina::getId).stream().anyMatch(id -> !id.equals(disciplina.getId()));
    }
}
