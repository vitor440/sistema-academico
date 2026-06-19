package com.sistema_escolar.sistema.escolar.validator;

import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocenteValidator {

    private final DocenteRepository docenteRepository;
    private final DisciplinaRepository disciplinaRepository;

    public void validar(Docente docente) {
        if (RegistroInternoOuCpfDuplicado(docente)) {
            throw new RegistroDuplicadoException("cpf, email ou matricula duplicado!");
        }

        if (quantidadeDisciplinas(docente)) {
            throw new RegistroConflitanteException("um docente só pode lesionar no máximo 3 disciplinas!");
        }
    }

    private boolean RegistroInternoOuCpfDuplicado(Docente docente) {
        Optional<Docente> docenteOpt = docenteRepository.findByCpfOrRegistroInternoOrEmail(docente.getCpf(), docente.getRegistroInterno(), docente.getEmail());

        if(docente.getId() == null) {
            return docenteOpt.isPresent();
        }

        return docenteOpt.map(Docente::getId).stream().anyMatch(id -> !id.equals(docente.getId()));
    }

    private boolean quantidadeDisciplinas(Docente docente) {
        int quantidadeDisciplinas = 0;

        if(docente.getDisciplinas() != null) {
            quantidadeDisciplinas = docente.getDisciplinas().size();
        }

        if(quantidadeDisciplinas > 3) {
            return true;
        }

        return false;
    }

    public void validaDelecao(Docente docente) {
        if (disciplinaRepository.existsByDocente(docente)) {
            throw new RuntimeException("Deleção não permitida!");
        }
    }
}
