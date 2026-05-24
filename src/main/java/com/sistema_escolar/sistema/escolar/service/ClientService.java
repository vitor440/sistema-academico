package com.sistema_escolar.sistema.escolar.service;

import com.sistema_escolar.sistema.escolar.data.dto.request.ClientRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ClientResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Client;
import org.springframework.data.domain.Page;


public interface ClientService {

    Client findByClientId(String clientId);

    ClientResponseDTO salvar(ClientRequestDTO requestDTO);

    ClientResponseDTO atualizar(Long id, ClientRequestDTO client);

    ClientResponseDTO obterPeloId(Long id);

    Page<ClientResponseDTO> listar(int pagina, int tamanho, String sortDirection);

    void deletarPeloId(Long id);
}
