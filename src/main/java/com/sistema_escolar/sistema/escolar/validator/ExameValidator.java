package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExameValidator {

    private final ExameRepository repository;

    public void validar(Exame exame) {
        if (registroDuplicado(exame)) {
            throw new RegistroDuplicadoException("Já existe um exame da disciplina " + exame.getDisciplina() + "na data " +
                    exame.getData() + "e hora:" + exame.getHora());
        }
    }

    private boolean registroDuplicado(Exame exame) {
        Optional<Exame> exameOpt = repository.findByDisciplinaAndDataAndHora(exame.getDisciplina(),
                exame.getData(), exame.getHora());

        if (exame.getId() == null) {
            return exameOpt.isPresent();
        }

        return exameOpt.map(Exame::getId).stream().anyMatch(id -> !id.equals(exame.getId()));
    }
}

