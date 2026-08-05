package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.DocenteRepository;
import com.sistema_escolar.sistema.escolar.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final DocenteRepository docenteRepository;

    public void validar(Usuario usuario) {
        if(duplicateUsernameOrEmail(usuario)) {
            throw new RegistroDuplicadoException("usuario com username ou email duplicado!");
        }
    }

    private boolean duplicateUsernameOrEmail(Usuario usuario) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail());

        if(usuario.getId() == null) {
            return usuarioOpt.isPresent();
        }

        return usuarioOpt.map(Usuario::getId).stream().anyMatch(id -> !id.equals(usuario.getId()));
    }

    public void validaDelecao(Usuario usuario) {
        if (alunoRepository.existsByUsuario(usuario) || docenteRepository.existsByUsuario(usuario)) {
            throw new RuntimeException("Deleção não permitida!");
        }
    }
}
