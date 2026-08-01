package com.sistema_escolar.sistema.escolar.service;


import com.sistema_escolar.sistema.escolar.data.dto.request.UsuarioRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.UsuarioResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;


public interface UsuarioService {

    Usuario findByUsername(String username);

    UsuarioResponseDTO salvarUsuarioAdmin(UsuarioRequestDTO requestDTO);

    Usuario salvarUsuario(Usuario usuario, String role);

    UsuarioResponseDTO atualizarUsuarioAdmin(Long id, UsuarioRequestDTO requestDTO);

    UsuarioResponseDTO obterPeloId(Long id);

    Page<UsuarioResponseDTO> listar(int pagina, int tamanho, String sortDirection, String role);

    void deletarPeloId(Long id);

    Usuario getUsuarioLogado();

    Usuario getUsuario(Long id);

    String encriptaSenha(String senha);

    void addRole(Usuario usuario, String role);

    UsuarioResponseDTO obterDados();

    Long countUsuario();


}
