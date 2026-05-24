package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DepartamentoValidator {

    private final DepartamentoRepository repository;

    public void validar(Departamento departamento) {
        if(nomeDuplicado(departamento)) {
            throw new RegistroDuplicadoException(departamento.getNome() + " já existe!");
        }
    }

    private boolean nomeDuplicado(Departamento departamento) {

        Optional<Departamento> departamentoOpt = repository.findByNome(departamento.getNome());

        if(departamento.getId() == null) {
            return departamentoOpt.isPresent();
        }

        return departamentoOpt.map(Departamento::getId).stream().anyMatch(id -> !id.equals(departamento.getId()));
    }
}
