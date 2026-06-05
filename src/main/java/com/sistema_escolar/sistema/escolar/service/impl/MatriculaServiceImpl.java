package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.MatriculaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.MatriculaMapper;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.model.enums.StatusDisciplina;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.MatriculaValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private final MatriculaMapper mapper;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final MatriculaValidator validator;
    private final UsuarioService usuarioService;

    @Override
    @Transactional
    public MatriculaResponseDTO salvar(MatriculaRequestDTO requestDTO) {
        Matricula matricula = mapper.toEntity(requestDTO);
        Aluno aluno = alunoService.getAluno(requestDTO.getAlunoId());
        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());

        matricula.setAluno(aluno);
        matricula.setStatus(StatusDisciplina.CURSANDO);
        matricula.setNotaFinal(0.0);
        matricula.setFaltas(0);
        matricula.setNotaFinal(0.0);
        matricula.setMedia(0.0);
        matricula.setEfetivado(false);
        matricula.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

        validator.validar(matricula, disciplina);
        disciplina.decrementaVaga(); // decrementa uma vaga e acrescenta um aluno matriculado.
        matricula.setDisciplina(disciplina);
        return mapper.toDTO(repository.save(matricula));
    }


    @Override
    @Transactional
    public MatriculaResponseDTO atualizar(Long id, MatriculaRequestDTO requestDTO) {
        Matricula matricula = getMatricula(id);
        Aluno aluno = alunoService.getAluno(requestDTO.getAlunoId());
        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());

        // se a disciplina for atualizada, vagas e alunos matriculados deverão ser ajustados
        if(!disciplina.equals(matricula.getDisciplina())) {

            if (matricula.getResultados().size() > 0) throw new RuntimeException();

            Disciplina disciplinaSubstituida = matricula.getDisciplina();
            disciplinaSubstituida.acrescentaVaga();
            disciplinaService.salvarEntidade(disciplinaSubstituida); // atualiza a quantidade de vagas e alunos matriculados da disciplina substituída.

            disciplina.decrementaVaga();
//            matricula.setResultados(aluno.getResultadosDeDisciplinas(disciplina));
//            matricula.calculaMedia(matricula.getResultados());
        }

        if (!aluno.equals(matricula.getAluno())) {

            if (matricula.getResultados() != null) throw new RuntimeException();

            matricula.setAluno(aluno);
        }

        matricula.setFaltas(requestDTO.getFaltas());

        validator.validar(matricula, disciplina);
        matricula.setDisciplina(disciplina);
        return mapper.toDTO(repository.save(matricula));
    }

    @Override
    public MatriculaResponseDTO obterPeloId(Long id) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();
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

        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

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
    public void modificaNotaFinal(Long matriculaId, Double notaFinal) {
        Matricula matricula = getMatricula(matriculaId);
        matricula.setNotaFinal(notaFinal);
        matricula.calculaMediaFinal(notaFinal);
        repository.save(matricula);
    }

    @Override
    public void modificaStatusSolicitacao(Long matriculaId, StatusSolicitacao statusSolicitacao) {
        repository.modificaStatusSolicitacao(matriculaId, statusSolicitacao);
    }

    @Override
    public MatriculaResponseDTO efetivarHistorico(Long id) {
        Matricula matricula = getMatricula(id);

        matricula.efetivar();

        return mapper.toDTO(repository.save(matricula));
    }
}
