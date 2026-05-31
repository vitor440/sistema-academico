package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.UsuarioRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.UsuarioResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.UsuarioMapper;
import com.sistema_escolar.sistema.escolar.model.Permission;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.PermissionRepository;
import com.sistema_escolar.sistema.escolar.repository.UsuarioRepository;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper mapper;
    private final UsuarioValidator validator;

    public Usuario findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Username não encontrado!"));
    }

    @Override
    public UsuarioResponseDTO salvar(UsuarioRequestDTO requestDTO, String role) {
        Usuario usuario = mapper.toEntity(requestDTO);
        usuario.setEnabled(true);
        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));

        Permission permission = permissionRepository.findByRole(role)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Não existe permission com role: " + role));

        usuario.setPermissions(List.of(permission));
        validator.validar(usuario);
        return mapper.toDTO(repository.save(usuario));
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO requestDTO) {
        Usuario usuario = getUsuario(id);
        usuario.setUsername(requestDTO.getUsername());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setSenha(requestDTO.getSenha());

        validator.validar(usuario);
        return mapper.toDTO(repository.save(usuario));
    }

    @Override
    public UsuarioResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getUsuario(id));
    }

    @Override
    public Page<UsuarioResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Direction direction = sortDirection.equalsIgnoreCase("ASC")? Direction.ASC: Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "username");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Usuario usuario = getUsuario(id);
        repository.delete(usuario);
    }



    private Usuario getUsuario(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuario não encontrado!"));
    }
}
