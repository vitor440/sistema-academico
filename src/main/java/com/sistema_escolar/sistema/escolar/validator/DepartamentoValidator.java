package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.repository.CursoRepository;
import com.sistema_escolar.sistema.escolar.repository.DepartamentoRepository;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DepartamentoValidator {

    private final DepartamentoRepository departamentoRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final DocenteRepository docenteRepository;

    public void validar(Departamento departamento) {
        if(nomeDuplicado(departamento)) {
            throw new RegistroDuplicadoException(departamento.getNome() + " já existe!");
        }
    }

    private boolean nomeDuplicado(Departamento departamento) {

        Optional<Departamento> departamentoOpt = departamentoRepository.findByNome(departamento.getNome());

        if(departamento.getId() == null) {
            return departamentoOpt.isPresent();
        }

        return departamentoOpt.map(Departamento::getId).stream().anyMatch(id -> !id.equals(departamento.getId()));
    }

    public void validaDelecao(Departamento departamento) {
        if (cursoRepository.existsByDepartamento(departamento) || disciplinaRepository.existsByDepartamento(departamento) || docenteRepository.existsByDepartamento(departamento)) {
            throw new RuntimeException("Deleção não permitida!");
        }
    }
}
