package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Exame;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:49-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class ExameMapperImpl implements ExameMapper {

    @Override
    public Exame toEntity(ExameRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Exame exame = new Exame();

        exame.setNome( requestDTO.getNome() );
        exame.setData( requestDTO.getData() );
        exame.setHora( requestDTO.getHora() );

        return exame;
    }

    @Override
    public ExameResponseDTO toDTO(Exame exame) {
        if ( exame == null ) {
            return null;
        }

        ExameResponseDTO exameResponseDTO = new ExameResponseDTO();

        exameResponseDTO.setId( exame.getId() );
        exameResponseDTO.setNome( exame.getNome() );
        exameResponseDTO.setData( exame.getData() );
        exameResponseDTO.setHora( exame.getHora() );

        exameResponseDTO.setDisciplinaId( exame.getDisciplina().getId() );

        return exameResponseDTO;
    }
}
