package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import org.springframework.data.domain.Page;

public interface ResultadoService {

    MatriculaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);

    MatriculaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);

    void deletarResultadoExame(Long id);

    ResultadoResponseDTO obterResultadoPeloId(Long id);

    Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    Page<ResultadoResponseDTO> listarPeloIdDaMatricula(Long id, int pagina, int tamanho, String sortDirection);
}
