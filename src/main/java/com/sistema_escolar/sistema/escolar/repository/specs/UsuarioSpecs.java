package com.sistema_escolar.sistema.escolar.repository.specs;

import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecs {

    public static Specification<Usuario> findByRole(String role) {
        return (root, query, cb) -> {
            Join<Object, Object> permissions = root.join("permissions");
            return cb.like(permissions.get("role"), role);
        };
    }
}
