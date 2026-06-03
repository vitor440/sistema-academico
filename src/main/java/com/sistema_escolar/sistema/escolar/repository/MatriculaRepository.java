package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Optional<Matricula> findByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);

    @Query(" SELECT m FROM Matricula m WHERE m.aluno = :aluno AND m.disciplina.horaInicio = :horaInicio")
    Optional<Matricula> findByAlunoAndHorario(Aluno aluno, LocalTime horaInicio);


    boolean existsByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);

    List<Matricula> findByAluno(Aluno aluno);


    @Modifying
    @Transactional
    @Query(" UPDATE Matricula m SET m.notaFinal = :notaFinal ")
    void modificaNotaFinal(Double notaFinal);

    @Modifying
    @Transactional
    @Query(" UPDATE Matricula m SET m.statusSolicitacao = :statusSolicitacao ")
    void modificaStatusSolicitacao(StatusSolicitacao statusSolicitacao);
}
