package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.MatriculaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import org.springframework.data.domain.Page;

public interface MatriculaService {

    MatriculaResponseDTO salvar(MatriculaRequestDTO requestDTO);


    MatriculaResponseDTO atualizar(Long id, MatriculaRequestDTO requestDTO);

    MatriculaResponseDTO obterPeloId(Long id);

    Page<MatriculaResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    Matricula getMatricula(Long id);

    void modificaNotaFinal(Double notaFinal);

    void modificaStatusSolicitacao(StatusSolicitacao statusSolicitacao);

    MatriculaResponseDTO efetivarHistorico(Long id);

}
