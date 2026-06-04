package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import org.springframework.data.domain.Page;

public interface DisciplinaService {

    DisciplinaResponseDTO salvar(DisciplinaRequestDTO requestDTO);

    void salvarEntidade(Disciplina disciplina);

    DisciplinaResponseDTO atualizar(Long id, DisciplinaRequestDTO requestDTO);

    DisciplinaResponseDTO obterPeloId(Long id);

    Page<DisciplinaResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    Disciplina getDisciplina(Long id);
}
