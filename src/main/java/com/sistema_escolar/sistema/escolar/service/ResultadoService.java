package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import org.springframework.data.domain.Page;

public interface ResultadoService {

    public AlunoDisciplinaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);

    public AlunoDisciplinaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO, Long resultadoId);

    public void deletarResultadoExame(Long id, Long resultadoId);

    public ResultadoResponseDTO obterResultadoPeloId(Long id, Long resultadoId);
}
