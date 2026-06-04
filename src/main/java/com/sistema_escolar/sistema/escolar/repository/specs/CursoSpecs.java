package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CursoSpecs {

    public static Specification<Curso> findByName(String nome) {
        return ((root, query, cb) ->
                cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%"));
    }

    public static Specification<Curso> findByArea(Areas area) {
        return ((root, query, cb) ->
                cb.equal(root.get("area"), area));
    }

    public static Specification<Curso> findByPeriodo(Periodo periodo) {
        return ((root, query, cb) ->
                cb.equal(root.get("periodo"), periodo));
    }

    public static Specification<Curso> findByNomeDepartamento(String nomeDepartamento) {
        return (root, query, cb) ->{
            Join<Object, Object> departamento = root.join("departamento");

            return cb.like(cb.upper(departamento.get("nome")), "%" + nomeDepartamento.toUpperCase() + "%");
        };
    }


}
