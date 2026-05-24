package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import org.springframework.data.domain.Page;

public interface ExameService {

    ExameResponseDTO salvar(ExameRequestDTO requestDTO);

    ExameResponseDTO atualizar(Long id, ExameRequestDTO requestDTO);

    ExameResponseDTO obterPeloId(Long id);

    Page<ExameResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
