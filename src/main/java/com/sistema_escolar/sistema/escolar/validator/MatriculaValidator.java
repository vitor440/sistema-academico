package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.HorarioDisciplinaRepository;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatriculaValidator {

    private final HorarioDisciplinaRepository horarioDisciplinaRepository;
    private final DisciplinaService disciplinaService;
    private final UsuarioService usuarioService;

    public void validar(Matricula matricula, Disciplina disciplina) {
        if (disciplina.getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + disciplina.getNome());
        }

        if (verificaConflitoDeHorarios(matricula, disciplina)) {
            throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + disciplina.getHorarios());
        }
    }



    // verifica se já existe uma matrícula de um aluno com horário e dia conflitante com outra matrícula

    private boolean verificaConflitoDeHorarios(Matricula matricula, Disciplina disciplina) {

        if(matricula.getDisciplina().getId().equals(disciplina.getId())) {
            return false;
        }

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

    // valida se um aluno ou docente tem permissão para visualizar uma matrícula
    // - aluno só pode ver matrículas pertencentes a ele.
    // - docente só pode ver matrículas de disciplinas que ele leciona.
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


    // valida se o aluno logado tem permissão para salvar ou atualizar uma matrícula
    // - um aluno só pode salvar/alterar uma matrícula que pertence a ele
    public void validaAlunoLogado(Matricula matricula) {
        if (usuarioService.getUsuarioLogado().getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioService.getUsuarioLogado().getAluno();

            if (!aluno.getId().equals(matricula.getAluno().getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para salvar/alterar essa matrícula!");
            }
        }
    }

    // valida se o docente logado tem permissão para salvar ou atualizar uma matrícula
    // um docente só pode salvar/alterar uma matrícula de uma disciplina que ele leciona
    public void validaDocenteLogado(Matricula matricula) {
        Docente docente = usuarioService.getUsuarioLogado().getDocente();

        if (!docente.getId().equals(matricula.getDisciplina().getDocente().getId())) {
            throw new AccessDeniedException("Acesso Negado: Você não tem permissão para salvar/alterar essa matrícula!");
        }
    }
}
