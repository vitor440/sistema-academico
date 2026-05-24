package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CursoValidator {

    private final CursoRepository repository;

    public void validar(Curso curso) {
        if(nomeDuplicado(curso)) {
            throw new RegistroDuplicadoException("curso com nome '" + curso.getNome() + "' já existe!");
        }


    }

    private boolean nomeDuplicado(Curso curso) {

        Optional<Curso> cursoOpt = repository.findByNome(curso.getNome());

        if(curso.getId() == null) {
            return cursoOpt.isPresent();
        }

        return cursoOpt.map(Curso::getId).stream().anyMatch(id -> !id.equals(curso.getId()));
    }
}
