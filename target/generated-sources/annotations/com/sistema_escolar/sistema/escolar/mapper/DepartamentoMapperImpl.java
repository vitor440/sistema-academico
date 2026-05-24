package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.DepartamentoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:49-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class DepartamentoMapperImpl implements DepartamentoMapper {

    @Override
    public Departamento toEntity(DepartamentoRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Departamento departamento = new Departamento();

        departamento.setNome( requestDTO.getNome() );
        departamento.setBloco( requestDTO.getBloco() );
        departamento.setSigla( requestDTO.getSigla() );

        return departamento;
    }

    @Override
    public DepartamentoResponseDTO toDTO(Departamento departamento) {
        if ( departamento == null ) {
            return null;
        }

        DepartamentoResponseDTO departamentoResponseDTO = new DepartamentoResponseDTO();

        departamentoResponseDTO.setId( departamento.getId() );
        departamentoResponseDTO.setNome( departamento.getNome() );
        departamentoResponseDTO.setBloco( departamento.getBloco() );
        departamentoResponseDTO.setSigla( departamento.getSigla() );

        return departamentoResponseDTO;
    }
}
