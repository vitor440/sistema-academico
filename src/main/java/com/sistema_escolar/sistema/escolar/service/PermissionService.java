package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.PermissionRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.PermissionResponseDTO;
import org.springframework.data.domain.Page;


public interface PermissionService {

    PermissionResponseDTO salvar(PermissionRequestDTO requestDTO);

    PermissionResponseDTO atualizar(Long id, PermissionRequestDTO requestDTO);

    Page<PermissionResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    PermissionResponseDTO obterPeloId(Long id);

    void deletarPeloId(Long id);
}
