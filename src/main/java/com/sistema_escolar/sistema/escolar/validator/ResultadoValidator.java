package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultadoValidator {

    private final ResultadoRepository repository;

    public void validar(Resultado resultado) {

    }

    public boolean registroDuplicado(Resultado resultado) {
        return false;
    }
}
