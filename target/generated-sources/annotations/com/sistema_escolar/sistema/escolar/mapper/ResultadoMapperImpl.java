package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T14:52:43-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class ResultadoMapperImpl implements ResultadoMapper {

    @Override
    public Resultado toEntity(ResultadoRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Resultado resultado = new Resultado();

        resultado.setNota( requestDTO.getNota() );
        resultado.setTipo( requestDTO.getTipo() );
        if ( requestDTO.getPeso() != null ) {
            resultado.setPeso( requestDTO.getPeso() );
        }

        return resultado;
    }

    @Override
    public ResultadoResponseDTO toDTO(Resultado resultado) {
        if ( resultado == null ) {
            return null;
        }

        ResultadoResponseDTO resultadoResponseDTO = new ResultadoResponseDTO();

        resultadoResponseDTO.setId( resultado.getId() );
        resultadoResponseDTO.setNota( resultado.getNota() );
        resultadoResponseDTO.setTipo( resultado.getTipo() );
        resultadoResponseDTO.setPeso( resultado.getPeso() );

        resultadoResponseDTO.setAlunoId( resultado.getAluno().getId() );
        resultadoResponseDTO.setExameId( resultado.getExame().getId() );
        resultadoResponseDTO.setAlunoDisciplinaId( resultado.getAlunoDisciplina().getId() );

        return resultadoResponseDTO;
    }
}
