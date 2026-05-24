package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.UsuarioRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.UsuarioResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Permission;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:50-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setUsername( requestDTO.getUsername() );
        usuario.setEmail( requestDTO.getEmail() );
        usuario.setSenha( requestDTO.getSenha() );

        return usuario;
    }

    @Override
    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        usuarioResponseDTO.setId( usuario.getId() );
        usuarioResponseDTO.setUsername( usuario.getUsername() );
        usuarioResponseDTO.setEmail( usuario.getEmail() );
        usuarioResponseDTO.setSenha( usuario.getSenha() );
        usuarioResponseDTO.setAccountNonExpired( usuario.isAccountNonExpired() );
        usuarioResponseDTO.setAccountNonLocked( usuario.isAccountNonLocked() );
        usuarioResponseDTO.setCredentialsNonExpired( usuario.isCredentialsNonExpired() );
        usuarioResponseDTO.setEnabled( usuario.isEnabled() );
        List<Permission> list = usuario.getPermissions();
        if ( list != null ) {
            usuarioResponseDTO.setPermissions( new ArrayList<Permission>( list ) );
        }

        return usuarioResponseDTO;
    }
}
