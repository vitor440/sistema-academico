package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<Resultado, Long>, JpaSpecificationExecutor<Resultado> {

    Optional<Resultado> findByMatriculaAndExame(Matricula matricula, Exame exame);

    @Query(" SELECT r FROM Resultado r JOIN Matricula m ON r.matricula.id = m.id where m.aluno = :aluno  ")
    Page<Resultado> obterResultadosDeAluno(Aluno aluno, Pageable pageable, Specification<Resultado> specs);

    @Query(" SELECT r FROM Resultado r JOIN Disciplina d ON r.matricula.disciplina.id = d.id where d.docente = :docente  ")
    Page<Resultado> obterResultadosDaDisciplinaDoDocente(Docente docente, Pageable pageable, Specification<Resultado> specs);

    Page<Resultado> findByMatricula(Matricula matricula, Pageable pageable);

    boolean existsByExame(Exame exame);

//    @Query(" SELECT EXTRACT(MONTH FROM r.dataCriacao) " +
//            "as mes, " +
//            "EXTRACT(YEAR FROM r.dataCriacao) " +
//            "as ano, " +
//            "AVG(r.nota) from Resultado r " +
//            "WHERE r.dataCriacao >= :data GROUP BY mes, ano")
//    List<Object> mediaNotasUltimosQuatroMeses(LocalDateTime data);


    @Query(" SELECT EXTRACT(MONTH FROM r.dataCriacao) " +
            "as mes, " +
            "EXTRACT(YEAR FROM r.dataCriacao) " +
            "as ano, " +
            "AVG(r.nota) from Resultado r " +
            "join Disciplina d on d.id = r.matricula.disciplina.id" +
            " WHERE r.dataCriacao >= :data and d.docente.id = :docenteId " +
            "GROUP BY mes, ano")
    List<Object[]> mediaNotasUltimosQuatroMeses(LocalDateTime data, Long docenteId);


}
