package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    Curso toEntity(CursoRequestDTO requestDTO);

    @Mapping(target = "departamentoId", expression = "java( curso.getDepartamento().getId() )")
    CursoResponseDTO toDTO(Curso curso);
}
