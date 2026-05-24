package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Docente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DisciplinaMapper {

    Disciplina toEntity(DisciplinaRequestDTO requestDTO);

    @Mapping(target = "departamentoId", expression = "java( disciplina.getDepartamento().getId() )")
    @Mapping(target = "docenteId", expression = "java( disciplina.getDocente().getId() )")
    DisciplinaResponseDTO toDTO(Disciplina disciplina);
}
