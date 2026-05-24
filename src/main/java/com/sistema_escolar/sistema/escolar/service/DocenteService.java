package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import org.springframework.data.domain.Page;

public interface DocenteService {

    DocenteResponseDTO salvar(DocenteRequestDTO requestDTO);

    DocenteResponseDTO atualizar(Long id, DocenteRequestDTO requestDTO);

    DocenteResponseDTO obterPeloId(Long id);

    Page<DocenteResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
