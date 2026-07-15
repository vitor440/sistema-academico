package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Docente;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class DocenteSpecs {

    public static Specification<Docente> findByNome(String nome) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Docente> findBydepartamentoId(Long idDepartamento) {
        return (root, query, cb) -> {
            Join<Object, Object> departamento = root.join("departamento");

            return cb.equal(departamento.get("id"),  idDepartamento);
        };

    }
}
