package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CursoService {

    CursoResponseDTO salvar(CursoRequestDTO requestDTO);

    CursoResponseDTO atualizar(Long id, CursoRequestDTO requestDTO);

    CursoResponseDTO obterPeloId(Long id);

    Page<CursoResponseDTO> listar(String nome, Areas area, Periodo periodo, Integer quantidadePeriodos,
                                  int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    Curso getCurso(Long id);

    List<Object[]> quantidadeDeAreas();

    List<Object[]> alunosPorCurso();

    Long countCurso();
}
