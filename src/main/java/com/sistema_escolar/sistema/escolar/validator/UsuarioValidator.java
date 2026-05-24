package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository repository;

    public void validar(Usuario usuario) {
        if(duplicateUsernameOrEmail(usuario)) {
            throw new RegistroDuplicadoException("usuario com username ou email duplicado!");
        }
    }

    private boolean duplicateUsernameOrEmail(Usuario usuario) {

        Optional<Usuario> usuarioOpt = repository.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail());

        if(usuario.getId() == null) {
            return usuarioOpt.isPresent();
        }

        return usuarioOpt.map(Usuario::getId).stream().anyMatch(id -> !id.equals(usuario.getId()));
    }
}
