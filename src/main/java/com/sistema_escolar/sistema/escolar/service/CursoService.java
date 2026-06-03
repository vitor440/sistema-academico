package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Curso;
import org.springframework.data.domain.Page;

public interface CursoService {

    CursoResponseDTO salvar(CursoRequestDTO requestDTO);

    CursoResponseDTO atualizar(Long id, CursoRequestDTO requestDTO);

    CursoResponseDTO obterPeloId(Long id);

    Page<CursoResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    Curso getCurso(Long id);

    Page<Curso> listarCursoPeloDepartamento(Long id, int pagina, int tamanho, String sortDirection);
}
