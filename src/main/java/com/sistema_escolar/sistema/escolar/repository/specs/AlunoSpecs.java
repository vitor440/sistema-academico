package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class AlunoSpecs {

    public static Specification<Aluno> findByName(String nome) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Aluno> findByCurso(Long idCurso) {
        return (root, query, cb) -> {
            Join<Object, Object> curso = root.join("curso");

            return cb.equal(curso.get("id"),  idCurso);
        };

    }
}
