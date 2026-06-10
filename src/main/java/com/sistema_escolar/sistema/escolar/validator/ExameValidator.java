package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExameValidator {

    private final ExameRepository repository;
    private final UsuarioService usuarioService;
    private final MatriculaRepository matriculaRepository;

    public void validar(Exame exame) {
        if (registroDuplicado(exame)) {
            throw new RegistroDuplicadoException("Já existe um exame da disciplina " + exame.getDisciplina().getNome() + " na data " +
                    exame.getData() + " e hora: " + exame.getHora());
        }
    }

    // verifica se há duplicidade (mesmo exame de uma disciplina na mesma data e hora)
    private boolean registroDuplicado(Exame exame) {
        Optional<Exame> exameOpt = repository.findByDisciplinaAndDataAndHora(exame.getDisciplina(),
                exame.getData(), exame.getHora());

        if (exame.getId() == null) {
            return exameOpt.isPresent();
        }

        return exameOpt.map(Exame::getId).stream().anyMatch(id -> !id.equals(exame.getId()));
    }

    // valida se docente tem permissão para salvar ou atualizar um exame
    // - um docente só pode salvar/alterar um exame de uma disciplina lecionada por ele
    public void validarDocenteLogado(Docente docente) {

        Docente docenteLogado = usuarioService.getUsuarioLogado().getDocente();

        if (!docenteLogado.getId().equals(docente.getId())) {
            throw new AccessDeniedException("Acesso Negado: Você não tem permissão para salvar/alterar esse exame!");
        }
    }

    // valida se um aluno ou docente tem permissão para visualizar um exame
    // - aluno só pode ver exame de disciplinas matriculas por ele.
    // - docente só pode ver exame de disciplinas que ele leciona.
    public void validarAcesso(Exame exame) {

        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioLogado.getAluno();
            boolean ehMatriculado = matriculaRepository.existsByAlunoAndDisciplina(aluno, exame.getDisciplina());

            if (!ehMatriculado) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para ver esse exame!");
            }
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docenteLogado = usuarioLogado.getDocente();
            Docente docente = exame.getDisciplina().getDocente();


            if (!docenteLogado.getId().equals(docente.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para ver esse exame!");
            }
        }
    }
}

