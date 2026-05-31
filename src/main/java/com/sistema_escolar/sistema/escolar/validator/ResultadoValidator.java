package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResultadoValidator {

    private final ResultadoRepository repository;

    public void validar(Resultado resultado) {
        if (registroDuplicado(resultado)) {
            throw new RegistroDuplicadoException("Já existe um resultad");
        }
    }

    public boolean registroDuplicado(Resultado resultado) {
        Optional<Resultado> resultadoOpt = repository.findByAlunoAndExame(resultado.getAluno(), resultado.getExame());

        if (resultado.getId() == null) {
            resultadoOpt.isPresent();
        }

        return resultadoOpt.map(Resultado::getId).stream().anyMatch(id -> !id.equals(resultado.getId()));
    }
}
