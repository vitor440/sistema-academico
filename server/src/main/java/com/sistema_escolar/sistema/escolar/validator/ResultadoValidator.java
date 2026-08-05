package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResultadoValidator {

    private final ResultadoRepository repository;
    private final UsuarioService usuarioService;
    private final MatriculaRepository matriculaRepository;

    public void validar(Resultado resultado) {
        if (registroDuplicado(resultado)) {
            throw new RegistroDuplicadoException("Já existe um resultado para este exame!");
        }
    }

    // verifica se há duplicidade (mesmo resultado para matrícula e exame)
    public boolean registroDuplicado(Resultado resultado) {
        Optional<Resultado> resultadoOpt = repository.findByMatriculaAndExame(resultado.getMatricula(), resultado.getExame());

        if (resultado.getId() == null) {
            resultadoOpt.isPresent();
        }

        return resultadoOpt.map(Resultado::getId).stream().anyMatch(id -> !id.equals(resultado.getId()));
    }

    // - valida se docente tem permissão para salvar ou atualizar um resultado
    // - um docente só pode salvar/alterar um resultado de uma disciplina lecionada por ele
    public void validarDocenteLogado(Docente docente) {
        Docente docenteLogado = usuarioService.getUsuarioLogado().getDocente();

        if (!docenteLogado.getId().equals(docente.getId())) {
            throw new AccessDeniedException("Acesso Negado: Você não tem permissão para salvar ou alterar esse resultado!");
        }
    }

    // - verifica se a matricula e o exame pertencem a mesma disciplina.
    public void verificaSeExameEMatriculaSaoDaMesmaDisciplina(Matricula matricula, Exame exame) {
        Disciplina disciplinaMatricula = matricula.getDisciplina();
        Disciplina disciplinaExame = exame.getDisciplina();

        if (!disciplinaMatricula.getId().equals(disciplinaExame.getId())) {
            throw new RegistroConflitanteException("não é permitido salvar um resultado de um exame para uma matrícula de disciplinas diferentes.");
        }
    }

    // - valida se um aluno ou docente tem permissão para visualizar um resultado
    // - aluno só pode ver resultados de disciplinas em que ele está matriculado.
    // - docente só pode ver resultados de disciplinas que ele leciona.
    public void validarAcesso(Resultado resultado) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();


        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno alunoLogado = usuarioLogado.getAluno();
            Aluno alunoResultado = resultado.getMatricula().getAluno();

            if (!alunoResultado.getId().equals(alunoLogado.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
            }
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docenteLogado = usuarioLogado.getDocente();
            Docente docenteResultado = resultado.getMatricula().getDisciplina().getDocente();

            if (!docenteResultado.getId().equals(docenteLogado.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
            }
        }
    }
}
