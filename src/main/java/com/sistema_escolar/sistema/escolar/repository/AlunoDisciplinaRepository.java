package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.Optional;

public interface AlunoDisciplinaRepository extends JpaRepository<AlunoDisciplina, Long> {

    Optional<AlunoDisciplina> findByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);

    @Query(" SELECT AD FROM AlunoDisciplina AD WHERE AD.aluno = :aluno AND AD.disciplina.horaInicio = :horaInicio")
    Optional<AlunoDisciplina> findByAlunoAndHorario(Aluno aluno, LocalTime horaInicio);



}
