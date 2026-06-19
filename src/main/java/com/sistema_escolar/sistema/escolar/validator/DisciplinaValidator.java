package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DisciplinaValidator {

    private final DisciplinaRepository disciplinaRepository;
    private final MatriculaRepository matriculaRepository;

    public void validar(Disciplina disciplina) {
        if (disciplinaDuplicada(disciplina)) {
            throw new RegistroDuplicadoException("Já existe uma disciplina com nome: ' " + disciplina.getNome() + "'e pertencente ao departamento: '"
                    + disciplina.getDepartamento().getNome() + "'");
        }
    }

    private boolean disciplinaDuplicada(Disciplina disciplina) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findByNomeAndDepartamento(disciplina.getNome(), disciplina.getDepartamento());

        if(disciplina.getId() == null) {
            return disciplinaOpt.isPresent();
        }

        return disciplinaOpt.map(Disciplina::getId).stream().anyMatch(id -> !id.equals(disciplina.getId()));
    }

    public void validaDelecao(Disciplina disciplina) {
        if (matriculaRepository.existsByDisciplina(disciplina)) {
            throw new RuntimeException("Deleção não permitida!");
        }
    }
}
