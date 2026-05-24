package com.sistema_escolar.sistema.escolar.data.dto.response;

import lombok.Data;

@Data
public class ClientResponseDTO {

    private Long id;

    private String clientId;

    private String clientSecret;

    private String redirectUri;


}
