package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:49-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class DisciplinaMapperImpl implements DisciplinaMapper {

    @Override
    public Disciplina toEntity(DisciplinaRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Disciplina disciplina = new Disciplina();

        disciplina.setNome( requestDTO.getNome() );
        disciplina.setLocalizacao( requestDTO.getLocalizacao() );
        if ( requestDTO.getAlunosMatriculados() != null ) {
            disciplina.setAlunosMatriculados( requestDTO.getAlunosMatriculados() );
        }
        if ( requestDTO.getVagas() != null ) {
            disciplina.setVagas( requestDTO.getVagas() );
        }
        disciplina.setDiaSemana( requestDTO.getDiaSemana() );
        disciplina.setPeriodo( requestDTO.getPeriodo() );
        disciplina.setHoraInicio( requestDTO.getHoraInicio() );

        return disciplina;
    }

    @Override
    public DisciplinaResponseDTO toDTO(Disciplina disciplina) {
        if ( disciplina == null ) {
            return null;
        }

        DisciplinaResponseDTO disciplinaResponseDTO = new DisciplinaResponseDTO();

        disciplinaResponseDTO.setId( disciplina.getId() );
        disciplinaResponseDTO.setNome( disciplina.getNome() );
        disciplinaResponseDTO.setLocalizacao( disciplina.getLocalizacao() );
        disciplinaResponseDTO.setAlunosMatriculados( disciplina.getAlunosMatriculados() );
        disciplinaResponseDTO.setVagas( disciplina.getVagas() );
        disciplinaResponseDTO.setDiaSemana( disciplina.getDiaSemana() );
        disciplinaResponseDTO.setPeriodo( disciplina.getPeriodo() );
        disciplinaResponseDTO.setHoraInicio( disciplina.getHoraInicio() );
        disciplinaResponseDTO.setHoraFim( disciplina.getHoraFim() );

        disciplinaResponseDTO.setDepartamentoId( disciplina.getDepartamento().getId() );
        disciplinaResponseDTO.setDocenteId( disciplina.getDocente().getId() );

        return disciplinaResponseDTO;
    }
}
