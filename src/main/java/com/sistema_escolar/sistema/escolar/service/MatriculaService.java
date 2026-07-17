package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.MatriculaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import org.springframework.data.domain.Page;

public interface MatriculaService {

    MatriculaResponseDTO salvar(MatriculaRequestDTO requestDTO);

    MatriculaResponseDTO obterPeloId(Long id);

    Page<MatriculaResponseDTO> listar(int pagina, int tamanho, String sortDirection,
                                      String nomeAluno,
                                      Long disciplinaId,
                                      StatusSolicitacao statusSolicitacao,
                                      StatusDisciplina statusDisciplina,
                                      Boolean efetivado,
                                      Integer semestre,
                                      Integer ano) ;

    void deletarPeloId(Long id);

    Matricula getMatricula(Long id);

    void modificaNotaFinal(Long matriculaId, Double notaFinal);

    void modificaStatusSolicitacao(Long matriculaId, StatusSolicitacao statusSolicitacao);

    MatriculaResponseDTO efetivarHistorico(Long id);

    void modificaFaltas(Long id, Integer faltas);

    Long countMatriculas();
}
