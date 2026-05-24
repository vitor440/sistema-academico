package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ExameRepository extends JpaRepository<Exame, Long> {

    Optional<Exame> findByDisciplinaAndDataAndHora(Disciplina disciplina, LocalDate data, LocalTime hora);
}
