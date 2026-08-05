package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>, JpaSpecificationExecutor<Disciplina> {

    Optional<Disciplina> findByNomeAndDepartamento(String nome, Departamento departamento);

    boolean existsByDepartamento(Departamento departamento);

    boolean existsByDocente(Docente docente);
}
