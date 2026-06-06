package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatriculaValidator {

    private final MatriculaRepository repository;

    public void validar(Matricula matricula, Disciplina disciplina) {
        if (disciplina.getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + disciplina.getNome());
        }

        if (verificaConflitoDeHorarios(matricula, disciplina)) {
          throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + disciplina.getHoraInicio());
        }
    }


    public boolean verificaConflitoDeHorarios(Matricula matricula, Disciplina disciplina) {
        Optional<Matricula> matriculaOpt = repository.findByAlunoAndHorario(matricula.getAluno(),
                 disciplina.getHoraInicio());

        if(matricula.getId() == null) {
            return matriculaOpt.isPresent();

        }

        return matriculaOpt.map(Matricula::getId).stream().anyMatch(id -> !id.equals(matricula.getId()));
    }
}
