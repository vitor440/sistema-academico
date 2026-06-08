package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DisciplinaValidator {

    private final DisciplinaRepository repository;

    public void validar(Disciplina disciplina) {
        if (disciplinaDuplicada(disciplina)) {
            throw new RegistroDuplicadoException("Já existe uma disciplina com nome: ' " + disciplina.getNome() + "'e pertencente ao departamento: '"
                    + disciplina.getDepartamento().getNome() + "'");
        }
    }

    private boolean disciplinaDuplicada(Disciplina disciplina) {
        Optional<Disciplina> disciplinaOpt = repository.findByNomeAndDepartamento(disciplina.getNome(), disciplina.getDepartamento());

        if(disciplina.getId() == null) {
            return disciplinaOpt.isPresent();
        }

        return disciplinaOpt.map(Disciplina::getId).stream().anyMatch(id -> !id.equals(disciplina.getId()));
    }
}
