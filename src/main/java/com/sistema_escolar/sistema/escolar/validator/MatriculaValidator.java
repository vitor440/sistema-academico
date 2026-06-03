package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatriculaValidator {

    private final MatriculaRepository repository;

    public void validar(Matricula matricula) {
        if (matricula.getDisciplina().getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + matricula.getDisciplina().getNome());
        }

        if (verificaConflitoDeHorarios(matricula)) {
          throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + matricula.getDisciplina().getHoraInicio());
        }
    }


    public boolean verificaConflitoDeHorarios(Matricula matricula) {
        Optional<Matricula> alunoDisciplinaOpt = repository.findByAlunoAndHorario(matricula.getAluno(),
                 matricula.getDisciplina().getHoraInicio());

        return alunoDisciplinaOpt.isPresent();
    }
}
