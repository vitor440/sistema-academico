package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Docente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocenteMapper {

    Docente toEntity(DocenteRequestDTO requestDTO);

    @Mapping(target = "departamentoId", expression = "java( docente.getDepartamento().getId() )")
    @Mapping(target = "usuarioId", expression = "java( docente.getUsuario().getId() )")
    DocenteResponseDTO toDTO(Docente docente);
}
