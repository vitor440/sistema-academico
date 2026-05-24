package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T14:52:42-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class AlunoDisciplinaMapperImpl implements AlunoDisciplinaMapper {

    @Autowired
    private ResultadoMapper resultadoMapper;

    @Override
    public AlunoDisciplina toEntity(AlunoDisciplinaRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        AlunoDisciplina alunoDisciplina = new AlunoDisciplina();

        if ( requestDTO.getFaltas() != null ) {
            alunoDisciplina.setFaltas( requestDTO.getFaltas() );
        }
        alunoDisciplina.setStatus( requestDTO.getStatus() );

        return alunoDisciplina;
    }

    @Override
    public AlunoDisciplinaResponseDTO toDTO(AlunoDisciplina alunoDisciplina) {
        if ( alunoDisciplina == null ) {
            return null;
        }

        AlunoDisciplinaResponseDTO alunoDisciplinaResponseDTO = new AlunoDisciplinaResponseDTO();

        alunoDisciplinaResponseDTO.setId( alunoDisciplina.getId() );
        alunoDisciplinaResponseDTO.setFaltas( alunoDisciplina.getFaltas() );
        alunoDisciplinaResponseDTO.setMedia( alunoDisciplina.getMedia() );
        alunoDisciplinaResponseDTO.setStatus( alunoDisciplina.getStatus() );
        alunoDisciplinaResponseDTO.setResultados( resultadoListToResultadoResponseDTOList( alunoDisciplina.getResultados() ) );

        alunoDisciplinaResponseDTO.setAlunoId( alunoDisciplina.getAluno().getId() );
        alunoDisciplinaResponseDTO.setDisciplinaId( alunoDisciplina.getDisciplina().getId() );

        return alunoDisciplinaResponseDTO;
    }

    protected List<ResultadoResponseDTO> resultadoListToResultadoResponseDTOList(List<Resultado> list) {
        if ( list == null ) {
            return null;
        }

        List<ResultadoResponseDTO> list1 = new ArrayList<ResultadoResponseDTO>( list.size() );
        for ( Resultado resultado : list ) {
            list1.add( resultadoMapper.toDTO( resultado ) );
        }

        return list1;
    }
}
