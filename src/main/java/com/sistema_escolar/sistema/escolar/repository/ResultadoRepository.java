package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    Optional<Resultado> findByMatriculaAndExame(Matricula matricula, Exame exame);

    @Query(" SELECT r FROM Resultado r JOIN Matricula m ON r.matricula.id = m.id where m.aluno = :aluno  ")
    Page<Resultado> obterResultadosDeAluno(Aluno aluno, Pageable pageable);

    @Query(" SELECT r FROM Resultado r JOIN Disciplina d ON r.matricula.disciplina.id = d.id where d.docente = :docente  ")
    Page<Resultado> obterResultadosDaDisciplinaDoDocente(Docente docente, Pageable pageable);
}
