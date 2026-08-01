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
import com.sistema_escolar.sistema.escolar.repository.specs.MatriculaSpecs;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.MatriculaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.springframework.data.domain.Sort.Direction;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final MatriculaMapper mapper;
    private final MatriculaValidator validator;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final UsuarioService usuarioService;


    @Override
    public MatriculaResponseDTO salvar(MatriculaRequestDTO requestDTO) {
        Matricula matricula = new Matricula();
        Aluno aluno = alunoService.getAluno(requestDTO.getAlunoId());

        validator.validaAlunoLogado(aluno);
        matricula.setAluno(aluno);

        Disciplina disciplina = disciplinaService.getDisciplina(requestDTO.getDisciplinaId());
        matricula.setDisciplina(disciplina);
        validator.validar(matricula);

        matricula.inicializaMatricula(); // inicializa dados de notas, nota final e média com valor 0.

        matricula.setAno(LocalDate.now().getYear());

        if (LocalDate.now().getMonth().getValue() < 7) {
            matricula.setSemestre(1);
        }else {
            matricula.setSemestre(2);
        }

        disciplina.decrementaVaga(); // decrementa uma vaga e acrescenta um aluno matriculado na disciplina.
        return mapper.toDTO(matriculaRepository.save(matricula));
    }


    @Override
    public MatriculaResponseDTO obterPeloId(Long id) {
        Matricula matricula = getMatricula(id);
        validator.validaAcesso(matricula);
        return mapper.toDTO(matricula);
    }


    @Override
    public Page<MatriculaResponseDTO> listar(int pagina, int tamanho, String sortDirection,
                                             String nomeAluno,
                                             Long disciplinaId,
                                             StatusSolicitacao statusSolicitacao,
                                             StatusDisciplina statusDisciplina,
                                             Boolean efetivado,
                                             Integer semestre,
                                             Integer ano) {
        Direction direction = sortDirection.equalsIgnoreCase("ASC")? Direction.ASC: Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "media");

        Specification<Matricula> specs = (root, query, cb) -> cb.conjunction();

        Usuario usuarioLogado = usuarioService.getUsuarioLogado();
        if (usuarioLogado.getRoles().contains("ALUNO")) {
            specs = specs.and(MatriculaSpecs.findByAluno(usuarioLogado.getAluno()));
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            specs = specs.and(MatriculaSpecs.findByDocente(usuarioLogado.getDocente()));
        }

        if (nomeAluno != null) specs = specs.and(MatriculaSpecs.findByNomeAluno(nomeAluno));
        if (disciplinaId != null) specs = specs.and(MatriculaSpecs.findByDisciplinaId(disciplinaId));
        if (statusSolicitacao != null) specs = specs.and(MatriculaSpecs.findByStatusSolicitacao(statusSolicitacao));
        if (statusDisciplina != null) specs = specs.and(MatriculaSpecs.findByStatusDisciplina(statusDisciplina));
        if (efetivado != null) specs = specs.and(MatriculaSpecs.findByEfetivado(efetivado));
        if (semestre != null) specs = specs.and(MatriculaSpecs.findBySemestre(semestre));
        if (ano != null) specs = specs.and(MatriculaSpecs.findByAno(ano));

        return matriculaRepository.findAll(specs, pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Matricula matricula = getMatricula(id);
        validator.validaAlunoLogado(matricula.getAluno());
        matriculaRepository.delete(matricula);
    }

    @Override
    public void modificaNotaFinal(Long matriculaId, Double notaFinal) {
        Matricula matricula = getMatricula(matriculaId);

        validator.validaDocenteLogado(matricula.getDisciplina().getDocente());
        matricula.setNotaFinal(notaFinal);
        matricula.calculaMediaFinal(notaFinal);
        matriculaRepository.save(matricula);
    }

    @Override
    public void modificaStatusSolicitacao(Long matriculaId, StatusSolicitacao statusSolicitacao) {
        Matricula matricula = getMatricula(matriculaId);

        validator.validaDocenteLogado(matricula.getDisciplina().getDocente());
        matriculaRepository.modificaStatusSolicitacao(matriculaId, statusSolicitacao);
    }

    @Override
    public MatriculaResponseDTO efetivarHistorico(Long id) {
        Matricula matricula = getMatricula(id);
        validator.validaDocenteLogado(matricula.getDisciplina().getDocente());
        matricula.efetivar();
        matricula.getDisciplina().acrescentaVaga();

        return mapper.toDTO(matriculaRepository.save(matricula));
    }

    @Override
    public void modificaFaltas(Long id, Integer faltas) {
        Matricula matricula = getMatricula(id);
        validator.validaDocenteLogado(matricula.getDisciplina().getDocente());

        if (faltas < 0)  {
            throw new RuntimeException("faltas menor que 0");
        }

        matricula.setFaltas(faltas);
        matriculaRepository.save(matricula);
    }

    @Override
    public Long countMatriculas() {
        return matriculaRepository.count();
    }


    @Override
    public Matricula getMatricula(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado!"));
    }
}
