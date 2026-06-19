package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {


//    @Query(" SELECT m FROM Matricula m WHERE m.aluno = :aluno AND m.disciplina.horario = :horaInicio ")
//    Optional<Matricula> findByAlunoAndHorario(Aluno aluno, LocalTime horario);


    boolean existsByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);

    Page<Matricula> findByAluno(Aluno aluno, Pageable pageable);

    @Query(" SELECT m FROM Matricula m where m.disciplina.docente = :docente ")
    Page<Matricula> obterMatriculasDocente(Docente docente, Pageable pageable);

    @Modifying
    @Transactional
    @Query(" UPDATE Matricula m SET m.statusSolicitacao = :statusSolicitacao WHERE m.id = :matriculaId ")
    void modificaStatusSolicitacao(Long matriculaId, StatusSolicitacao statusSolicitacao);


    boolean existsByAluno(Aluno aluno);

    boolean existsByDisciplina(Disciplina disciplina);
}
