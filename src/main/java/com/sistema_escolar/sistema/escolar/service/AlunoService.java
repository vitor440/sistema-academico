package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import org.springframework.data.domain.Page;

public interface AlunoService {

    AlunoResponseDTO salvar(AlunoRequestDTO requestDTO);

    AlunoResponseDTO atualizar(Long id, AlunoRequestDTO requestDTO);

    AlunoResponseDTO obterPeloId(Long id);

    Page<AlunoResponseDTO> listar(String nome, Long idCurso, int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    AlunoResponseDTO atualizarAlunoLogado(AlunoRequestDTO requestDTO);

    AlunoResponseDTO obterAlunoLogado();

    Aluno getAluno(Long id);
}
