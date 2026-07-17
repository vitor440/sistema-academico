package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.enums.StatusExame;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ExameRepository extends JpaRepository<Exame, Long>, JpaSpecificationExecutor<Exame> {

    Optional<Exame> findByDisciplinaAndDataAndHora(Disciplina disciplina, LocalDate data, LocalTime hora);

    @Query(" SELECT e FROM Exame e JOIN Matricula m ON e.disciplina.id = m.disciplina.id WHERE m.aluno = :aluno ")
    Page<Exame> obterExamesDeAluno(Aluno aluno, Pageable pageable, Specification<Exame> specs);


    @Query(" SELECT e FROM Exame e JOIN Disciplina d ON e.disciplina.id = d.id WHERE d.docente = :docente ")
    Page<Exame> obterExamesDoDocente(Docente docente, Pageable pageable, Specification<Exame> specs);

}
