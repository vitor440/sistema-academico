package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Docente;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:50-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class DocenteMapperImpl implements DocenteMapper {

    @Override
    public Docente toEntity(DocenteRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Docente docente = new Docente();

        docente.setCpf( requestDTO.getCpf() );
        docente.setNome( requestDTO.getNome() );
        docente.setEmail( requestDTO.getEmail() );
        docente.setTelefone( requestDTO.getTelefone() );
        docente.setDataNascimento( requestDTO.getDataNascimento() );
        docente.setFormacao( requestDTO.getFormacao() );
        docente.setSalario( requestDTO.getSalario() );

        return docente;
    }

    @Override
    public DocenteResponseDTO toDTO(Docente docente) {
        if ( docente == null ) {
            return null;
        }

        DocenteResponseDTO docenteResponseDTO = new DocenteResponseDTO();

        docenteResponseDTO.setId( docente.getId() );
        docenteResponseDTO.setRegistroInterno( docente.getRegistroInterno() );
        docenteResponseDTO.setCpf( docente.getCpf() );
        docenteResponseDTO.setNome( docente.getNome() );
        docenteResponseDTO.setEmail( docente.getEmail() );
        docenteResponseDTO.setTelefone( docente.getTelefone() );
        docenteResponseDTO.setDataNascimento( docente.getDataNascimento() );
        docenteResponseDTO.setFormacao( docente.getFormacao() );
        docenteResponseDTO.setSalario( docente.getSalario() );

        docenteResponseDTO.setDepartamentoId( docente.getDepartamento().getId() );
        docenteResponseDTO.setUsuarioId( docente.getUsuario().getId() );

        return docenteResponseDTO;
    }
}
