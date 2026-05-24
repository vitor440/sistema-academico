package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.ClientRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ClientResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Client;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:50-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toEntity(ClientRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setClientId( requestDTO.getClientId() );
        client.setClientSecret( requestDTO.getClientSecret() );

        return client;
    }

    @Override
    public ClientResponseDTO toDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();

        clientResponseDTO.setId( client.getId() );
        clientResponseDTO.setClientId( client.getClientId() );
        clientResponseDTO.setClientSecret( client.getClientSecret() );
        clientResponseDTO.setRedirectUri( client.getRedirectUri() );

        return clientResponseDTO;
    }
}
