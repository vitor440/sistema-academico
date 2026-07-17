package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class MatriculaSpecs {

    public static Specification<Matricula> findByAluno(Aluno aluno) {
        return (root, query, cb) ->
                cb.equal(root.get("aluno"), aluno);
    }

    public static Specification<Matricula> findByDocente(Docente docente) {
        return (root, query, cb) -> {
            Join<Object, Object> disciplina = root.join("disciplina");
            return cb.equal(disciplina.get("docente"), docente);
        };
    }

    public static Specification<Matricula> findByNomeAluno(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> aluno = root.join("aluno");
            return cb.like(cb.upper(aluno.get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }

    public static Specification<Matricula> findByDisciplinaId(Long disciplinaId) {
        return (root, query, cb) -> {
            Join<Object, Object> disciplina = root.join("disciplina");
            return cb.equal(disciplina.get("id"), disciplinaId);
        };
    }

    public static Specification<Matricula> findByStatusDisciplina(StatusDisciplina status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Matricula> findByStatusSolicitacao(StatusSolicitacao status) {
        return (root, query, cb) ->
                cb.equal(root.get("statusSolicitacao"), status);
    }

    public static Specification<Matricula> findByEfetivado(boolean efetivado) {
        return (root, query, cb) ->
                cb.equal(root.get("efetivado"), efetivado);
    }

    public static Specification<Matricula> findBySemestre(Integer semestre) {
        return (root, query, cb) ->
                cb.equal(root.get("semestre"), semestre);
    }

    public static Specification<Matricula> findByAno(Integer ano) {
        return (root, query, cb) ->
                cb.equal(root.get("ano"), ano);
    }

}
