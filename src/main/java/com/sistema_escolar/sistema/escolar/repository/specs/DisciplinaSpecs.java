package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class DisciplinaSpecs {

    public static Specification<Disciplina> findByNome(String nome) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Disciplina> findByDocenteId(Long docenteId) {
        return (root, query, cb) -> {
            Join<Object, Object> docente = root.join("docente");
            return cb.equal(docente.get("Id"), docenteId);
        };
    }

    public static Specification<Disciplina> findBySemestre(Integer semestre) {
        return (root, query, cb) ->
                cb.equal(root.get("semestre"), semestre);
    }

    public static Specification<Disciplina> findByAno(Integer ano) {
        return (root, query, cb) ->
                cb.equal(root.get("ano"), ano);
    }
}
