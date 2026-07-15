package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Disciplina;
import org.springframework.data.jpa.domain.Specification;

public class DisciplinaSpecs {

    public static Specification<Disciplina> findByNome(String nome) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }
}
