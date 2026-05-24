package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import org.springframework.data.domain.Page;

public interface ResultadoService {

    ResultadoResponseDTO salvar(ResultadoRequestDTO requestDTO);

    ResultadoResponseDTO atualizar(Long id, ResultadoRequestDTO requestDTO);

    ResultadoResponseDTO obterPeloId(Long id);

    Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
