package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlunoValidator {

    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;

    public void validar(Aluno aluno) {
        if (cpfMatriculaOuEmailDuplicado(aluno)) {
            throw new RegistroDuplicadoException("cpf, email ou matricula duplicado!");
        }
    }

    private boolean cpfMatriculaOuEmailDuplicado(Aluno aluno) {
        Optional<Aluno> alunoOpt = alunoRepository.findByCpfOrMatriculaOrEmail(aluno.getCpf(), aluno.getMatricula(), aluno.getEmail());

        if(aluno.getId() == null) {
            return alunoOpt.isPresent();
        }

        return alunoOpt.map(Aluno::getId).stream().anyMatch(id -> !id.equals(aluno.getId()));
    }

    public void validaDelecao(Aluno aluno) {
        if (matriculaRepository.existsByAluno(aluno)) {
            throw new RuntimeException("Deleção não permitida!");
        }
    }
}
