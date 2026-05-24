package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import org.springframework.data.domain.Page;

public interface AlunoDisciplinaService {

    AlunoDisciplinaResponseDTO salvar(AlunoDisciplinaRequestDTO requestDTO);

    AlunoDisciplinaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO);

    AlunoDisciplinaResponseDTO atualizar(Long id, AlunoDisciplinaRequestDTO requestDTO);

    AlunoDisciplinaResponseDTO obterPeloId(Long id);

    Page<AlunoDisciplinaResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
