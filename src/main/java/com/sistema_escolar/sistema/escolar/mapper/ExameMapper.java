package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.model.Exame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExameMapper {

    Exame toEntity(ExameRequestDTO requestDTO);

    @Mapping(target = "disciplinaId", expression = "java( exame.getDisciplina().getId() )")
    ExameResponseDTO toDTO(Exame exame);
}
