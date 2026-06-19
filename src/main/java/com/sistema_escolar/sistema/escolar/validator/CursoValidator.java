package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CursoValidator {

    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;

    public void validar(Curso curso) {
        if(nomeDuplicado(curso)) {
            throw new RegistroDuplicadoException("curso com nome '" + curso.getNome() + "' já existe!");
        }


    }

    private boolean nomeDuplicado(Curso curso) {

        Optional<Curso> cursoOpt = cursoRepository.findByNome(curso.getNome());

        if(curso.getId() == null) {
            return cursoOpt.isPresent();
        }

        return cursoOpt.map(Curso::getId).stream().anyMatch(id -> !id.equals(curso.getId()));
    }

    public void validaDelecao(Curso curso) {
        if (alunoRepository.existsByCurso(curso)) {
            throw new RuntimeException("Deleção não permitida!");
        }
    }
}
