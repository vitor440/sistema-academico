package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.repository.HorarioDisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatriculaValidator {

    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    public void validar(Matricula matricula, Disciplina disciplina) {
        if (disciplina.getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + disciplina.getNome());
        }

       if (verificaConflitoDeHorarios(matricula, disciplina)) {
         throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + disciplina.getHorarios());
       }
    }


    private boolean verificaConflitoDeHorarios(Matricula matricula, Disciplina disciplina) {

        List<HorarioDisciplina> horariosNovaDisciplina = horarioDisciplinaRepository.findByDisciplina(disciplina);
        List<HorarioDisciplina> horariosDoAluno = horarioDisciplinaRepository.obterHorariosDeAluno(matricula.getAluno());


        for (HorarioDisciplina horarioDisciplina : horariosNovaDisciplina) {
            for (HorarioDisciplina horarioAluno : horariosDoAluno) {
                if (horarioDisciplina.getHorario().equals(horarioAluno.getHorario())
                        && horarioDisciplina.getDiaSemana() == horarioAluno.getDiaSemana()) {

                    return true;
                }
            }
        }

        return false;
    }
}
