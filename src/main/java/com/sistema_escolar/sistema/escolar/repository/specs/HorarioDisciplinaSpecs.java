package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class HorarioDisciplinaSpecs {

    public static Specification<HorarioDisciplina> findBySemestre(Integer semestre) {
        return (root, query, cb) -> {

            Join<Object, Object> disciplina = root.join("disciplina");
            Join<Object, Object> matriculas = disciplina.join("matriculas");

            return cb.equal(matriculas.get("semestre"), semestre);
        };
    }

    public static Specification<HorarioDisciplina> findByAno(Integer ano) {
        return (root, query, cb) -> {

            Join<Object, Object> disciplina = root.join("disciplina");
            Join<Object, Object> matriculas = disciplina.join("matriculas");

            return cb.equal(matriculas.get("ano"), ano);
        };
    }

    public static Specification<HorarioDisciplina> findByAluno(Aluno aluno) {
        return (root, query, cb) -> {

            Join<Object, Object> disciplina = root.join("disciplina");
            Join<Object, Object> matriculas = disciplina.join("matriculas");

            return cb.equal(matriculas.get("aluno"), aluno);
        };
    }

    public static Specification<HorarioDisciplina> findByDocente(Docente docente) {
        return (root, query, cb) -> {

            Join<Object, Object> disciplina = root.join("disciplina");

            return cb.equal(disciplina.get("docente"), docente);
        };
    }
}
