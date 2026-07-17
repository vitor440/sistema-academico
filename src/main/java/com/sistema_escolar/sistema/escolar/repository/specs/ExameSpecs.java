package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.model.enums.StatusExame;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ExameSpecs {

    public static Specification<Exame> greaterThanData(LocalDate data) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataCriacao"), data);
    }

    public static Specification<Exame> findBySemestre(Integer semestre) {
        return (root, query, cb) ->
                cb.equal(root.get("semestre"), semestre);
    }

    public static Specification<Exame> findByAno(Integer ano) {
        return (root, query, cb) ->
                cb.equal(root.get("ano"), ano);
    }

    public static Specification<Exame> findByStatus(StatusExame status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Exame> findByDisciplinaId(Long disciplinaId) {
        return (root, query, cb) -> {
            Join<Object, Object> disciplina = root.join("disciplina");
            return cb.equal(disciplina.get("id"), disciplinaId);
        };
    }

    public static Specification<Exame> findByDocente(Docente docente) {
        return (root, query, cb) -> {
            Join<Object, Object> disciplina = root.join("disciplina");
            return cb.equal(disciplina.get("docente"), docente);
        };
    }
}
