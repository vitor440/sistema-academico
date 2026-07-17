package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ResultadoSpecs {

    public static Specification<Resultado> greaterThanData(LocalDate data) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataCriacao"), data);
    }

    public static Specification<Resultado> findBySemestre(Integer semestre) {
        return (root, query, cb) ->
                cb.equal(root.get("semestre"), semestre);
    }

    public static Specification<Resultado> findByAno(Integer ano) {
        return (root, query, cb) ->
                cb.equal(root.get("ano"), ano);
    }

    public static Specification<Resultado> findByDisciplinaId(Long disciplinaId) {
        return (root, query, cb) -> {
            Join<Object, Object> exame = root.join("exame");
            Join<Object, Object> disciplina = exame.join("disciplina");
            return cb.equal(disciplina.get("id"), disciplinaId);
        };
    }

    public static Specification<Resultado> findByExameId(Long exameId) {
        return (root, query, cb) -> {
            Join<Object, Object> exame = root.join("exame");
            return cb.equal(exame.get("id"), exameId);
        };
    }

    public static Specification<Resultado> findByDocente(Docente docente) {
        return (root, query, cb) -> {
            Join<Object, Object> matricula = root.join("matricula");
            Join<Object, Object> disciplina = matricula.join("disciplina");
            return cb.equal(disciplina.get("docente"), docente);
        };
    }

    public static Specification<Resultado> findByAluno(Aluno aluno) {
        return (root, query, cb) -> {
            Join<Object, Object> matricula = root.join("matricula");
            return cb.equal(matricula.get("aluno"), aluno);
        };
    }


}
