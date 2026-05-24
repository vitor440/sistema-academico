package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    Aluno toEntity(AlunoRequestDTO requestDTO);

    @Mapping(target = "cursoId", expression = "java( aluno.getCurso().getId() )")
    @Mapping(target = "usuarioId", expression = "java( aluno.getUsuario().getId() )")
    AlunoResponseDTO toDTO(Aluno aluno);
}
