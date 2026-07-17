package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.enums.StatusExame;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ExameService {

    ExameResponseDTO salvar(ExameRequestDTO requestDTO);

    ExameResponseDTO atualizar(Long id, ExameRequestDTO requestDTO);

    ExameResponseDTO obterPeloId(Long id);

    Page<ExameResponseDTO> listar(int pagina, int tamanho, String sortDirection, LocalDate data, Integer semestre, Integer ano, Long disciplinaId,
                                  StatusExame status);

    void deletarPeloId(Long id);

    Exame getExame(Long id);

    void atualizaStatusExame(Long id, StatusExame status);

    Long countExame();
}
