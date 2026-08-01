package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface HorarioDisciplinaRepository extends JpaRepository<HorarioDisciplina, Long>, JpaSpecificationExecutor<HorarioDisciplina> {


    Page<HorarioDisciplina> findByDisciplina(Disciplina disciplina, Pageable pageable);

    List<HorarioDisciplina> findByDisciplina(Disciplina disciplina);

    @Query(" SELECT h FROM HorarioDisciplina h JOIN Matricula m ON h.disciplina = m.disciplina WHERE m.aluno = :aluno and m.semestre = :semestre and m.ano = :ano")
    List<HorarioDisciplina> obterHorariosDoAluno(Aluno aluno, Integer semestre, Integer ano);

    Optional<HorarioDisciplina> findByHorarioAndDiaSemanaAndDisciplina(LocalTime horario, DiasSemana diasSemana, Disciplina disciplina);
}
