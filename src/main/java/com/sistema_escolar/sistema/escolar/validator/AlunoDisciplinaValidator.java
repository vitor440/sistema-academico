package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.repository.AlunoDisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlunoDisciplinaValidator {

    private final AlunoDisciplinaRepository repository;

    public void validar(AlunoDisciplina alunoDisciplina) {
        if (alunoDisciplina.getDisciplina().getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + alunoDisciplina.getDisciplina().getNome());
        }

        if (verificaConflitoDeHorarios(alunoDisciplina)) {
          throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + alunoDisciplina.getDisciplina().getHoraInicio());
        }
    }

    public boolean registroDuplicado(AlunoDisciplina alunoDisciplina) {
        Optional<AlunoDisciplina> alunoDisciplinaOpt = repository.findByAlunoAndDisciplina(alunoDisciplina.getAluno(),
                alunoDisciplina.getDisciplina());

        if (alunoDisciplina.getId() == null) {
            return alunoDisciplinaOpt.isPresent();
        }

        return alunoDisciplinaOpt.map(AlunoDisciplina::getId).stream().anyMatch(id -> !id.equals(alunoDisciplina.getId()));
    }

    public boolean verificaConflitoDeHorarios(AlunoDisciplina alunoDisciplina) {
        Optional<AlunoDisciplina> alunoDisciplinaOpt = repository.findByAlunoAndHorario(alunoDisciplina.getAluno(),
                 alunoDisciplina.getDisciplina().getHoraInicio());

        return alunoDisciplinaOpt.isPresent();
    }
}
