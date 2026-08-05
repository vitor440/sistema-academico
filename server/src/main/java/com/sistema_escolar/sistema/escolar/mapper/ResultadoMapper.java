package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultadoMapper {

    Resultado toEntity(ResultadoRequestDTO requestDTO);


    @Mapping(target = "exameId", expression = "java( resultado.getExame().getId() )")
    @Mapping(target = "matriculaId", expression = "java( resultado.getMatricula().getId() )")
    @Mapping(target = "aluno", expression = "java( resultado.getMatricula().getAluno().getNome() )")
    ResultadoResponseDTO toDTO(Resultado resultado);
}
