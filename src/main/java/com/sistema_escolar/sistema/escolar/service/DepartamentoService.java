package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.DepartamentoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import org.springframework.data.domain.Page;

public interface DepartamentoService {

    DepartamentoResponseDTO salvar(DepartamentoRequestDTO requestDTO);

    DepartamentoResponseDTO atualizar(Long id, DepartamentoRequestDTO requestDTO);

    DepartamentoResponseDTO obterPeloId(Long id);

    Page<DepartamentoResponseDTO> listar(int pagina, String nome, int tamanho, String sortDirection);

    void deletarPeloId(Long id);

    Departamento getDepartamento(Long id);

    Long countDepartamento();
}
