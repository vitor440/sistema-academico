package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Resultado;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ResultadoSpecs {

    public static Specification<Resultado> greaterThanData(LocalDate data) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataCriacao"), data);
    }
}
