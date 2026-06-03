package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.MatriculaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.MatriculaMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.validator.MatriculaValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.*;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private final MatriculaMapper mapper;
    private final AlunoService alunoService;
    private final DisciplinaRepository disciplinaRepository;
    private final DisciplinaService disciplinaService;
    private final MatriculaValidator validator;

    @Override
    @Transactional
    public MatriculaResponseDTO salvar(MatriculaRequestDTO requestDTO) {
        Matricula matricula = mapper.toEntity(requestDTO);
        Aluno aluno = alunoService.getAluno(requestDTO.getAlunoId());
        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setStatus(StatusDisciplina.CURSANDO);
        matricula.setNotaFinal(0.0);
        matricula.setEfetivado(false);
        matricula.setStatusSolicitacao(StatusSolicitacao.PENDENTE);
        matricula.calculaMedia(aluno.getResultados());

        validator.validar(matricula);
        disciplina.setVagas(disciplina.getVagas() - 1); // decrementa uma vaga

        disciplina.setAlunosMatriculados(disciplina.getAlunosMatriculados() + 1); // acrescenta + 1 aluno matriculado na disciplina
        return mapper.toDTO(repository.save(matricula));
    }


    @Override
    @Transactional
    public MatriculaResponseDTO atualizar(Long id, MatriculaRequestDTO requestDTO) {
        Matricula matricula = getMatricula(id);

        Aluno aluno = alunoService.getAluno(requestDTO.getAlunoId());
        matricula.setAluno(aluno);
        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());

        // se a disciplina for atualizada, vagas e alunos matriculados deverão ser ajustados
        if(!disciplina.equals(matricula.getDisciplina())) {

            Disciplina disciplina2 = matricula.getDisciplina();
            disciplina2.setVagas(disciplina2.getVagas() + 1);
            disciplina2.setAlunosMatriculados(disciplina2.getAlunosMatriculados() - 1);

            disciplinaRepository.save(disciplina2);

            disciplina.setVagas(disciplina.getVagas() - 1);
            disciplina.setAlunosMatriculados(disciplina.getAlunosMatriculados() + 1);
        }

        matricula.setDisciplina(disciplina);
        matricula.setFaltas(requestDTO.getFaltas());
        matricula.calculaMedia(aluno.getResultados());

        validator.validar(matricula);
        return mapper.toDTO(repository.save(matricula));
    }

    @Override
    public MatriculaResponseDTO obterPeloId(Long id) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Matricula matricula = getMatricula(id);

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            boolean ehMatriculado = repository.existsByAlunoAndDisciplina(usuarioLogado.getAluno(), matricula.getDisciplina());

            if (!ehMatriculado) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para ver essa matrícula!");
            }
        }
        return mapper.toDTO(matricula);
    }

    @Override
    public Page<MatriculaResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Direction direction = sortDirection.equalsIgnoreCase("ASC")? Direction.ASC: Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "media");

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioLogado.getRoles().contains("ALUNO")) {

            List<Matricula> matriculas = repository.findByAluno(usuarioLogado.getAluno());
            return new PageImpl<>(matriculas, pageable, matriculas.size()).map(mapper::toDTO);
        }

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Matricula matricula = getMatricula(id);
        repository.delete(matricula);
    }

    @Override
    public Matricula getMatricula(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado!"));
    }

    @Override
    public void modificaNotaFinal(Double notaFinal) {
        repository.modificaNotaFinal(notaFinal);
    }

    @Override
    public void modificaStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
        repository.modificaStatusSolicitacao(statusSolicitacao);
    }

    @Override
    public MatriculaResponseDTO efetivarHistorico(Long id) {
        Matricula matricula = getMatricula(id);

        matricula.efetivar();

        return mapper.toDTO(matricula);
    }
}
