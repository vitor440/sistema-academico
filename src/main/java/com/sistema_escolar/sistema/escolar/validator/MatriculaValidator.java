package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.HorarioDisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatriculaValidator {

    private final HorarioDisciplinaRepository horarioDisciplinaRepository;
    private final MatriculaRepository matriculaRepository;
    private final UsuarioService usuarioService;

    public void validar(Matricula matricula, Disciplina disciplina) {
        if (disciplina.getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + disciplina.getNome());
        }

       if (verificaConflitoDeHorarios(matricula, disciplina)) {
         throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + disciplina.getHorarios());
       }
    }


    public void validaAcesso(Matricula matricula) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioLogado.getAluno();
            boolean matriculaPertenceAoAluno = aluno.equals(matricula.getAluno());

            if (!matriculaPertenceAoAluno) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para ver essa matrícula!");
            }
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docenteLogado = usuarioLogado.getDocente();
            Docente docente = matricula.getDisciplina().getDocente();

            if (!docenteLogado.getId().equals(docente.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para ver essa matrícula!");
            }
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
