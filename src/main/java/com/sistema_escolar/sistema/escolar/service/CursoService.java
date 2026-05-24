package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import org.springframework.data.domain.Page;

public interface CursoService {

    CursoResponseDTO salvar(CursoRequestDTO requestDTO);

    CursoResponseDTO atualizar(Long id, CursoRequestDTO requestDTO);

    CursoResponseDTO obterPeloId(Long id);

    Page<CursoResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
