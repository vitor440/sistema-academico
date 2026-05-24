package com.sistema_escolar.sistema.escolar.mapper;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T00:01:49-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class AlunoMapperImpl implements AlunoMapper {

    @Override
    public Aluno toEntity(AlunoRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Aluno aluno = new Aluno();

        aluno.setMatricula( requestDTO.getMatricula() );
        aluno.setCpf( requestDTO.getCpf() );
        aluno.setNome( requestDTO.getNome() );
        aluno.setEmail( requestDTO.getEmail() );
        aluno.setTelefone( requestDTO.getTelefone() );
        aluno.setDataNascimento( requestDTO.getDataNascimento() );
        aluno.setCursoPeriodo( requestDTO.getCursoPeriodo() );

        return aluno;
    }

    @Override
    public AlunoResponseDTO toDTO(Aluno aluno) {
        if ( aluno == null ) {
            return null;
        }

        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();

        alunoResponseDTO.setId( aluno.getId() );
        alunoResponseDTO.setMatricula( aluno.getMatricula() );
        alunoResponseDTO.setCpf( aluno.getCpf() );
        alunoResponseDTO.setNome( aluno.getNome() );
        alunoResponseDTO.setEmail( aluno.getEmail() );
        alunoResponseDTO.setTelefone( aluno.getTelefone() );
        alunoResponseDTO.setDataNascimento( aluno.getDataNascimento() );
        alunoResponseDTO.setCursoPeriodo( aluno.getCursoPeriodo() );

        alunoResponseDTO.setCursoId( aluno.getCurso().getId() );
        alunoResponseDTO.setUsuarioId( aluno.getUsuario().getId() );

        return alunoResponseDTO;
    }
}
