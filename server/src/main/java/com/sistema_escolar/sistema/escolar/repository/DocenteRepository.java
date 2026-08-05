package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long>, JpaSpecificationExecutor<Docente> {

    Optional<Docente> findByCpfOrRegistroInternoOrEmail(String cpf, String registroInterno, String email);

    Optional<Docente> findByUsuario(Usuario usuario);

    boolean existsByDepartamento(Departamento departamento);

    boolean existsByUsuario(Usuario usuario);
}
