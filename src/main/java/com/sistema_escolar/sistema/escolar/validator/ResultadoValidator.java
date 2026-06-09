package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.model.Usuario;
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

    public boolean registroDuplicado(Resultado resultado) {
        Optional<Resultado> resultadoOpt = repository.findByMatriculaAndExame(resultado.getMatricula(), resultado.getExame());

        if (resultado.getId() == null) {
            resultadoOpt.isPresent();
        }

        return resultadoOpt.map(Resultado::getId).stream().anyMatch(id -> !id.equals(resultado.getId()));
    }

    public void validarDocenteLogado(Docente docente) {
        Docente docenteLogado = usuarioService.getUsuarioLogado().getDocente();

        if (!docenteLogado.getId().equals(docente.getId())) {
            throw new AccessDeniedException("Acesso Negado: Você não tem permissão para salvar/alterar esse resultado!");
        }
    }

    public void validarAcesso(Resultado resultado) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();


        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioLogado.getAluno();
            boolean ehMatriculado = matriculaRepository.existsByAlunoAndDisciplina(aluno, resultado.getMatricula().getDisciplina());

            if (!ehMatriculado) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
            }
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docenteLogado = usuarioLogado.getDocente();
            Docente docente = resultado.getMatricula().getDisciplina().getDocente();

            if (!docente.getId().equals(docenteLogado.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
            }
        }
    }
}
