package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    Optional<Resultado> findByMatriculaAndExame(Matricula matricula, Exame exame);
}
