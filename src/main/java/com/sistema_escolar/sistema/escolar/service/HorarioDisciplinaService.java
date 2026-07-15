package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.HorarioDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.HorarioDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HorarioDisciplinaService {

    DisciplinaResponseDTO salvar (Long id, HorarioDisciplinaRequestDTO requestDTO);

    HorarioDisciplinaResponseDTO atualizar(Long id, HorarioDisciplinaRequestDTO requestDTO);

    HorarioDisciplinaResponseDTO obterPeloId(Long id);

    Page<HorarioDisciplinaResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    HorarioDisciplina getHorarioDisciplina(Long id);

    Page<HorarioDisciplinaResponseDTO> obterHorariosPeloIdDaDisciplina(Long id, int pagina, int tamanho, String sortDirection);

    Page<HorarioDisciplinaResponseDTO> obterHorariosAlunoPeloSemestreEAno(Long alunoId, Integer semestre, Integer ano, Integer pagina, Integer tamanho, String sortDirection);
}
