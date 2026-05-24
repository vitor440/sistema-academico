package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.ClientRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ClientResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientRequestDTO requestDTO);

    ClientResponseDTO toDTO(Client client);
}
