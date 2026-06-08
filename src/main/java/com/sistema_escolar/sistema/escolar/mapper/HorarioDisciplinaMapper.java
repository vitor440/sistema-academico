package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.HorarioDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.HorarioDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HorarioDisciplinaMapper {

    HorarioDisciplina toEntity(HorarioDisciplinaRequestDTO requestDTO);

    @Mapping(target = "disciplinaId", expression = "java( horarioDisciplina.getDisciplina().getId() )")
    HorarioDisciplinaResponseDTO toDTO(HorarioDisciplina horarioDisciplina);
}
