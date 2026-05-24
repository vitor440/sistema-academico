package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ResultadoMapper.class)
public interface AlunoDisciplinaMapper {

    AlunoDisciplina toEntity(AlunoDisciplinaRequestDTO requestDTO);

    @Mapping(target = "alunoId", expression = "java( alunoDisciplina.getAluno().getId() )")
    @Mapping(target = "disciplinaId", expression = "java( alunoDisciplina.getDisciplina().getId() )")
    AlunoDisciplinaResponseDTO toDTO(AlunoDisciplina alunoDisciplina);

}
