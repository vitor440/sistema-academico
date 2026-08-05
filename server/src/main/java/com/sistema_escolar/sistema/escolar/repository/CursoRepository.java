package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

    Optional<Curso> findByNome(String nome);

    boolean existsByDepartamento(Departamento departamento);

    @Query("select c.area, count(c) from Curso c group by c.area order by count(c) desc")
    List<Object[]> quantidadeDeAreas(Pageable pageable);

    @Query(" select c.nome, count(c) from Curso c join Aluno a on a.curso.id  = c.id group by c.nome order by count(a) desc")
    List<Object[]> alunosPorCurso(Pageable pageable);

}
