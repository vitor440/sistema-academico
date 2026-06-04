package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ClientRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ClientResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ClientMapper;
import com.sistema_escolar.sistema.escolar.model.Client;
import com.sistema_escolar.sistema.escolar.repository.ClientRepository;
import com.sistema_escolar.sistema.escolar.service.ClientService;
import com.sistema_escolar.sistema.escolar.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final String REDIRECT_URI = "http://localhost:8080/authorized";

    private final ClientRepository repository;
    private final ClientValidator validator;
    private final PasswordEncoder encoder;
    private final ClientMapper mapper;

    @Override
    public Client findByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("client_id inexistente!"));

    }

    @Override
    public ClientResponseDTO salvar(ClientRequestDTO requestDTO) {
        Client client = mapper.toEntity(requestDTO);
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        client.setRedirectUri(REDIRECT_URI);
        validator.validar(client);

        return mapper.toDTO(repository.save(client));
    }

    @Override
    public ClientResponseDTO atualizar(Long id, ClientRequestDTO requestDTO) {
        Client clientSalvo = getClientSalvo(id);

        clientSalvo.setClientId(requestDTO.getClientId());
        clientSalvo.setClientSecret(encoder.encode(clientSalvo.getClientSecret()));
        clientSalvo.setRedirectUri(REDIRECT_URI);

        validator.validar(clientSalvo);
        return mapper.toDTO(repository.save(clientSalvo));
    }

    @Override
    public ClientResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getClientSalvo(id));
    }

    @Override
    public Page<ClientResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "clientId");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Client client = getClientSalvo(id);
        repository.delete(client);
    }



    private Client getClientSalvo(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Client não encontrado!"));
    }
}
