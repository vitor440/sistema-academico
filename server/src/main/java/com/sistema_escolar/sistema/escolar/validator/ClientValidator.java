package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Client;
import com.sistema_escolar.sistema.escolar.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository repository;

    public void validar(Client client) {
        if(duplicateClientId(client)) {
            throw new RegistroDuplicadoException("client_id duplicado!");
        }
    }

    private boolean duplicateClientId(Client client) {

        Optional<Client> clientOpt = repository.findByClientId(client.getClientId());

        if(client.getId() == null) {
            return clientOpt.isPresent();
        }

        return clientOpt.map(Client::getId).stream().anyMatch(id -> !id.equals(client.getId()));
    }
}
