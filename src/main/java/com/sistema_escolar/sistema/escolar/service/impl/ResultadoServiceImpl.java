package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.MatriculaMapper;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.ResultadoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final MatriculaService matriculaService;
    private final MatriculaRepository repository;
    private final MatriculaMapper mapper;
    private final ResultadoMapper resultadoMapper;
    private final ExameService exameService;
    private final ResultadoValidator validator;
    private final ResultadoRepository resultadoRepository;
    private final UsuarioService usuarioService;



    @Override
    public MatriculaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO) {
        Resultado resultado = resultadoMapper.toEntity(resultadoRequestDTO);
        Matricula matricula = matriculaService.getMatricula(id);
        Exame exame = exameService.getExame(resultadoRequestDTO.getExameId());

        Docente docente = exame.getDisciplina().getDocente();
        validarDocenteLogado(docente);

        if(!matricula.getDisciplina().getId().equals(exame.getDisciplina().getId())) {
            throw new RegistroConflitanteException("Você está tentando atribuir resultado de um examde de "
                    + exame.getDisciplina().getNome()
                    + " para uma matrícula da disciplina de " + matricula.getDisciplina().getNome());
        }

        resultado.setMatricula(matricula);
        resultado.setExame(exame);
        validator.validar(resultado);

        matricula.addResultado(resultado);

        return mapper.toDTO(repository.save(matricula));
    }

    @Override
    public MatriculaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO) {
        Resultado resultado = getResultado(id);

        Docente docente = resultado.getExame().getDisciplina().getDocente();
        validarDocenteLogado(docente);

        Exame exame = exameService.getExame(resultadoRequestDTO.getExameId());

        resultado.setNota(resultadoRequestDTO.getNota());
        Matricula matricula = resultado.getMatricula();
        resultado.setExame(exame);

        if(!matricula.getDisciplina().getId().equals(exame.getDisciplina().getId())) {
            throw new RegistroConflitanteException("Você está tentando atribuir resultado de um examde de "
                    + exame.getDisciplina().getNome()
                    + " para uma matrícula da disciplina de " + matricula.getDisciplina().getNome());
        }

        validator.validar(resultado);

        matricula.getResultados().add(resultado);
        matricula.calculaMedia(matricula.getResultados());
        return mapper.toDTO(repository.save(matricula));

        }

    public Resultado getResultado(Long id) {
        return resultadoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Resultado não encontrado"));
    }



    @Override
    public void deletarResultadoExame(Long id) {
        Resultado resultado = getResultado(id);

        Docente docente = resultado.getExame().getDisciplina().getDocente();
        validarDocenteLogado(docente);

        Matricula matricula = resultado.getMatricula();
        matricula.getResultados().remove(resultado);
        matricula.calculaMedia(matricula.getResultados());
        repository.save(matricula);
    }

    @Override
    public ResultadoResponseDTO obterResultadoPeloId(Long id) {
        Resultado resultado = getResultado(id);
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            boolean ehMatriculado = repository.existsByAlunoAndDisciplina(usuarioLogado.getAluno(), resultado.getMatricula().getDisciplina());

            if (!ehMatriculado) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
            }
        }
        if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docenteLogado = usuarioLogado.getDocente();
            Docente docente = resultado.getMatricula().getDisciplina().getDocente();

            if (!docente.getId().equals(docenteLogado.getId())) {
                throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
            }
        }

        return resultadoMapper.toDTO(resultado);
    }

    @Override
    @Transactional
    public Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nota");
        List<Resultado> resultados = List.of();
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioLogado.getAluno();
            return resultadoRepository.obterResultadosDeAluno(aluno, pageable).map(resultadoMapper::toDTO);
        }

        else if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docente = usuarioLogado.getDocente();
            return resultadoRepository.obterResultadosDaDisciplinaDoDocente(docente, pageable).map(resultadoMapper::toDTO);
        }

        else {
            return resultadoRepository.findAll(pageable).map(resultadoMapper::toDTO);
        }
    }

    @Override
    @Transactional
    public Page<ResultadoResponseDTO> listarPeloIdDaMatricula(Long id, int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nota");

        Matricula matricula = matriculaService.getMatricula(id);

        return resultadoRepository.findByMatricula(matricula, pageable).map(resultadoMapper::toDTO);
    }

    private void validarDocenteLogado(Docente docente) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();
        Docente docenteLogado = usuarioLogado.getDocente();

        if (!docenteLogado.getId().equals(docente.getId())) {
            throw new AccessDeniedException("Acesso Negado: Você não tem permissão para salvar/alterar esse resultado!");
        }}
}
