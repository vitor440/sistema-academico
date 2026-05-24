package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultadoMapper {

    Resultado toEntity(ResultadoRequestDTO requestDTO);

    @Mapping(target = "alunoId", expression = "java( resultado.getAluno().getId() )")
    @Mapping(target = "exameId", expression = "java( resultado.getExame().getId() )")
    @Mapping(target = "alunoDisciplinaId", expression = "java( resultado.getAlunoDisciplina().getId() )")
    ResultadoResponseDTO toDTO(Resultado resultado);
}
