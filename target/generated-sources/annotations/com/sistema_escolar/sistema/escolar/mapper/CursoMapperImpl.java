package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Curso;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:50-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class CursoMapperImpl implements CursoMapper {

    @Override
    public Curso toEntity(CursoRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setNome( requestDTO.getNome() );
        curso.setArea( requestDTO.getArea() );
        curso.setQuantidadeAlunos( requestDTO.getQuantidadeAlunos() );
        curso.setPeriodo( requestDTO.getPeriodo() );
        curso.setQuantidadePeriodos( requestDTO.getQuantidadePeriodos() );

        return curso;
    }

    @Override
    public CursoResponseDTO toDTO(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoResponseDTO cursoResponseDTO = new CursoResponseDTO();

        cursoResponseDTO.setId( curso.getId() );
        cursoResponseDTO.setNome( curso.getNome() );
        cursoResponseDTO.setArea( curso.getArea() );
        cursoResponseDTO.setQuantidadeAlunos( curso.getQuantidadeAlunos() );
        cursoResponseDTO.setPeriodo( curso.getPeriodo() );
        cursoResponseDTO.setQuantidadePeriodos( curso.getQuantidadePeriodos() );

        return cursoResponseDTO;
    }
}
