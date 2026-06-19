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
    private final UsuarioService usuarioService;

    public void validar(Matricula matricula) {
        if (matricula.getDisciplina().getVagas() == 0) {
            throw new RegistroConflitanteException("não há vagas para a disciplina: " + matricula.getDisciplina().getNome());
        }

        verificaConflitoDeHorarios(matricula);
    }



    // verifica se já existe uma matrícula de um aluno com horário e dia conflitante com outra matrícula

    private void verificaConflitoDeHorarios(Matricula matricula) {

//        if(matricula.getDisciplina().getId().equals(disciplina.getId())) {
//            return false;
//        }

        List<HorarioDisciplina> horariosNovaDisciplina = horarioDisciplinaRepository.findByDisciplina(matricula.getDisciplina());
        List<HorarioDisciplina> horariosDoAluno = horarioDisciplinaRepository.obterHorariosDoAluno(matricula.getAluno());


        for (HorarioDisciplina horarioDisciplina : horariosNovaDisciplina) {
            for (HorarioDisciplina horarioAluno : horariosDoAluno) {
                if (horarioDisciplina.getHorario().equals(horarioAluno.getHorario())
                        && horarioDisciplina.getDiaSemana() == horarioAluno.getDiaSemana()) {

                    throw new RegistroConflitanteException("Aluno já está matriculado em uma disciplina no horário: " + horarioDisciplina.getHorario() + " e na " + horarioDisciplina.getDiaSemana());
                }
            }
        }

    }

    // - valida se um aluno ou docente tem permissão para visualizar uma matrícula
    // - Aluno: verifica se a matrícula a ser visualizada pertence ao aluno.
    // - docente: verifica se a matrícula pertence a uma disciplina lecionada pelo docente.
    public void validaAcesso(Matricula matricula) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno alunoLogado = usuarioLogado.getAluno();
            Aluno aluno = matricula.getAluno();

            if (!alunoLogado.getId().equals(aluno.getId())) {
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


    // - valida se o aluno logado tem permissão para realizar a matrícula
    // - um aluno só pode salvar/alterar uma matrícula que pertence a ele
    public void validaAlunoLogado(Aluno aluno) {
        if (usuarioService.getUsuarioLogado().getRoles().contains("ALUNO")) {
            Aluno alunoLogado = usuarioService.getUsuarioLogado().getAluno();

            if (!alunoLogado.getId().equals(aluno.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para realizar essa matrícula!");
            }
        }
    }

    // - valida se o docente logado tem permissão para alterar dados de uma matrícula
    // - um docente só pode alterar dados de uma matrícula de uma disciplina que ele leciona
    public void validaDocenteLogado(Docente docente) {
        if (usuarioService.getUsuarioLogado().getRoles().contains("DOCENTE")) {
            Docente docenteLogado = usuarioService.getUsuarioLogado().getDocente();

            if (!docenteLogado.getId().equals(docente.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para realizar essa matrícula!");
            }
        }

    }
}
