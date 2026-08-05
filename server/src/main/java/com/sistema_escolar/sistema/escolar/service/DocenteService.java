package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Docente;
import org.springframework.data.domain.Page;

public interface DocenteService {

    DocenteResponseDTO salvar(DocenteRequestDTO requestDTO);

    DocenteResponseDTO atualizar(Long id, DocenteRequestDTO requestDTO);

    DocenteResponseDTO obterPeloId(Long id);

    Page<DocenteResponseDTO> listar(int pagina, int tamanho, String sortDirection, String nome, Long departamentoId);

    DocenteResponseDTO atualizarDocenteLogado(DocenteRequestDTO requestDTO);

    DocenteResponseDTO obterDocenteLogado();

    void deletarPeloId(Long id);

    Docente getDocente(Long id);

    Long countDocente();
}
