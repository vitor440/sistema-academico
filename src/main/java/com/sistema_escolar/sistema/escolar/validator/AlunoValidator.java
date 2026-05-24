package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlunoValidator {

    private final AlunoRepository repository;

    public void validar(Aluno aluno) {
        if (cpfMatriculaOuEmailDuplicado(aluno)) {
            throw new RegistroDuplicadoException("cpf, email ou matricula duplicado!");
        }
    }

    private boolean cpfMatriculaOuEmailDuplicado(Aluno aluno) {
        Optional<Aluno> alunoOpt = repository.findByCpfOrMatriculaOrEmail(aluno.getCpf(), aluno.getMatricula(), aluno.getEmail());

        if(aluno.getId() == null) {
            return alunoOpt.isPresent();
        }

        return alunoOpt.map(Aluno::getId).stream().anyMatch(id -> !id.equals(aluno.getId()));
    }
}
