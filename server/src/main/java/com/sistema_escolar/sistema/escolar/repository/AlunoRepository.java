package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {

    Optional<Aluno> findByCpfOrMatriculaOrEmail(String cpf, String matricula, String email);

    Optional<Aluno> findByUsuario(Usuario usuario);

    boolean existsByCurso(Curso curso);

    boolean existsByUsuario(Usuario usuario);
}
