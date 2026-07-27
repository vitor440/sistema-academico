package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.DiasSemana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface HorarioDisciplinaRepository extends JpaRepository<HorarioDisciplina, Long> {


    Page<HorarioDisciplina> findByDisciplina(Disciplina disciplina, Pageable pageable);

    List<HorarioDisciplina> findByDisciplina(Disciplina disciplina);

    @Query(" SELECT h FROM HorarioDisciplina h JOIN Matricula m ON h.disciplina = m.disciplina WHERE m.aluno = :aluno ")
    List<HorarioDisciplina> obterHorariosDoAluno(Aluno aluno);

    @Query(" SELECT h FROM HorarioDisciplina h JOIN Matricula m ON h.disciplina = m.disciplina WHERE m.aluno = :aluno ")
    Page<HorarioDisciplina> obterHorariosDoAlunoPaginado(Aluno aluno, Pageable pageable);


    @Query(" SELECT h FROM HorarioDisciplina h JOIN Disciplina d ON h.disciplina.id = d.id WHERE d.docente = :docente ")
    Page<HorarioDisciplina> obterHorariosDoDocente(Docente docente, Pageable pageable);

    Optional<HorarioDisciplina> findByHorarioAndDiaSemanaAndDisciplina(LocalTime horario, DiasSemana diasSemana, Disciplina disciplina);
}
