package com.sistema_escolar.sistema.escolar.service;


import com.sistema_escolar.sistema.escolar.data.dto.request.UsuarioRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.UsuarioResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import org.springframework.data.domain.Page;


public interface UsuarioService {

    Usuario findByUsername(String username);

    UsuarioResponseDTO salvar(UsuarioRequestDTO requestDTO);

    UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO requestDTO);

    UsuarioResponseDTO obterPeloId(Long id);

    Page<UsuarioResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
