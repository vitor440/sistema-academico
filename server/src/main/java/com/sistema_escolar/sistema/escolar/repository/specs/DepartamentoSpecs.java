package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Departamento;
import org.springframework.data.jpa.domain.Specification;

public class DepartamentoSpecs {

    public static Specification<Departamento> findByNome(String nome) {
       return (root, query, cb) ->
               cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }
}
