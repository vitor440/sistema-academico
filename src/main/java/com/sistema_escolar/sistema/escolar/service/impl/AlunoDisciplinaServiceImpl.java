package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.AlunoDisciplinaMapper;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.AlunoDisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.service.AlunoDisciplinaService;
import com.sistema_escolar.sistema.escolar.validator.AlunoDisciplinaValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.*;

@Service
@RequiredArgsConstructor
public class AlunoDisciplinaServiceImpl implements AlunoDisciplinaService {

    private final AlunoDisciplinaRepository repository;
    private final AlunoDisciplinaMapper mapper;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final AlunoDisciplinaValidator validator;
    private final ExameRepository exameRepository;

    @Override
    @Transactional
    public AlunoDisciplinaResponseDTO salvar(AlunoDisciplinaRequestDTO requestDTO) {
        AlunoDisciplina alunoDisciplina = mapper.toEntity(requestDTO);
        Aluno aluno = getAluno(requestDTO.getAlunoId());
        Disciplina disciplina = getDisciplina(requestDTO.getDisciplinaId());
        alunoDisciplina.setAluno(aluno);
        alunoDisciplina.setDisciplina(disciplina);
        alunoDisciplina.calculaMedia(aluno.getResultados());

        validator.validar(alunoDisciplina);
        disciplina.setVagas(disciplina.getVagas() - 1); // decrementa uma vaga

        disciplina.setAlunosMatriculados(disciplina.getAlunosMatriculados() + 1); // acrescenta + 1 aluno matriculado na disciplina
        return mapper.toDTO(repository.save(alunoDisciplina));
    }


    @Override
    @Transactional
    public AlunoDisciplinaResponseDTO atualizar(Long id, AlunoDisciplinaRequestDTO requestDTO) {
        AlunoDisciplina alunoDisciplina = getAlunoDisciplina(id);

        Aluno aluno = getAluno(requestDTO.getAlunoId());
        alunoDisciplina.setAluno(aluno);
        Disciplina disciplina = getDisciplina(requestDTO.getDisciplinaId());

        // se a disciplina for atualizada, vagas e alunos matriculados deverão ser ajustados
        if(!disciplina.equals(alunoDisciplina.getDisciplina())) {

            Disciplina disciplina2 = alunoDisciplina.getDisciplina();
            disciplina2.setVagas(disciplina2.getVagas() + 1);
            disciplina2.setAlunosMatriculados(disciplina2.getAlunosMatriculados() - 1);
            disciplinaRepository.save(disciplina2);

            disciplina.setVagas(disciplina.getVagas() - 1);
            disciplina.setAlunosMatriculados(disciplina.getAlunosMatriculados() + 1);
        }

        alunoDisciplina.setDisciplina(disciplina);
        alunoDisciplina.setFaltas(requestDTO.getFaltas());
        alunoDisciplina.calculaMedia(aluno.getResultados());
        alunoDisciplina.setStatus(requestDTO.getStatus());

        validator.validar(alunoDisciplina);
        return mapper.toDTO(repository.save(alunoDisciplina));
    }

    @Override
    public AlunoDisciplinaResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getAlunoDisciplina(id));
    }

    @Override
    public Page<AlunoDisciplinaResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Direction direction = sortDirection.equalsIgnoreCase("ASC")? Direction.ASC: Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "media");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        AlunoDisciplina alunoDisciplina = getAlunoDisciplina(id);
        repository.delete(alunoDisciplina);
    }

    private AlunoDisciplina getAlunoDisciplina(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado!"));
    }

    private Disciplina getDisciplina(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Disciplina não encontrada!"));
    }

    private Aluno getAluno(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Aluno não encontrado!"));
    }

    private Exame getExame(Long id) {
        return exameRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Exame não encontrado!"));
    }
}
