package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.MesAnoEMedia;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ResultadoService {

//    MatriculaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);
    ResultadoResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);

    //MatriculaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);

    ResultadoResponseDTO atualizaNota(Long id, Double nota);

    void deletarResultadoExame(Long id);

    ResultadoResponseDTO obterResultadoPeloId(Long id);

    Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection, Integer semestre, Integer ano, Long disciplinaId);

    Page<ResultadoResponseDTO> listarPeloIdDaMatricula(Long id, int pagina, int tamanho, String sortDirection);

    List<MesAnoEMedia> mediaNotasUltimosQuatroMeses();


}
