package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ExameRepository extends JpaRepository<Exame, Long> {

    Optional<Exame> findByDisciplinaAndDataAndHora(Disciplina disciplina, LocalDate data, LocalTime hora);

    @Query(" SELECT e FROM Exame e JOIN Matricula m ON e.disciplina.id = m.disciplina.id WHERE m.aluno = :aluno ")
    List<Exame> obterExamesDeAluno(Aluno aluno);


    @Query(" SELECT e FROM Exame e JOIN Disciplina d ON e.disciplina.id = d.id WHERE d.docente = :docente ")
    List<Exame> obterExamesDoDocente(Docente docente);

}
