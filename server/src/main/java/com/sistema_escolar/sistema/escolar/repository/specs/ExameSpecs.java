package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.model.enums.StatusExame;
import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ExameSpecs {

    public static Specification<Exame> greaterThanData(LocalDate data) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("data"), data);
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

    public static Specification<Exame> findByAluno(Aluno aluno) {
        return (root, query, cb) -> {
            Join<Object, Object> disciplina = root.join("disciplina");
            Join<Object, Object> matricula = disciplina.join("matriculas");
            return cb.equal(matricula.get("aluno"), aluno);
        };
    }

    public static Specification<Exame> findByTipo(TipoExame tipo) {
        return (root, query, cb) ->
                cb.equal(root.get("tipo"), tipo);
    }
}
