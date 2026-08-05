package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.MatriculaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ResultadoMapper.class)
public interface MatriculaMapper {

    Matricula toEntity(MatriculaRequestDTO requestDTO);

    @Mapping(target = "alunoId", expression = "java( matricula.getAluno().getId() )")
    @Mapping(target = "disciplinaId", expression = "java( matricula.getDisciplina().getId() )")
    @Mapping(target = "nomeAluno", expression = "java( matricula.getAluno().getNome() )")
    @Mapping(target = "disciplina", expression = "java( matricula.getDisciplina().getNome() )")
    @Mapping(target = "matricula", expression = "java( matricula.getAluno().getMatricula() )")
    MatriculaResponseDTO toDTO(Matricula matricula);

}
