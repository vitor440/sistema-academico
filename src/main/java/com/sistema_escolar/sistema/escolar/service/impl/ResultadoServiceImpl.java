package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.MatriculaMapper;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import com.sistema_escolar.sistema.escolar.validator.ResultadoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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



    @Override
    public MatriculaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO) {
        Resultado resultado = resultadoMapper.toEntity(resultadoRequestDTO);
        Matricula matricula = matriculaService.getMatricula(id);
        Exame exame = exameService.getExame(resultadoRequestDTO.getExameId());

        resultado.setMatricula(matricula);
        resultado.setExame(exame);
        validator.validar(resultado);

        matricula.addResultado(resultado);

        return mapper.toDTO(repository.save(matricula));
    }

    @Override
    public MatriculaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO, Long resultadoId) {
        Matricula matricula = matriculaService.getMatricula(id);
        Exame exame = exameService.getExame(resultadoRequestDTO.getExameId());

        for (Resultado resultado : matricula.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {
                resultado.setNota(resultadoRequestDTO.getNota());
                resultado.setMatricula(matricula);
                resultado.setExame(exame);
                validator.validar(resultado);

                matricula.calculaMedia(matricula.getResultados());
                return mapper.toDTO(repository.save(matricula));
            }
        }

        throw new RegistroNaoEncontradoException("Não existe resultado com ID: " + resultadoId);
    }

    @Override
    public void deletarResultadoExame(Long id, Long resultadoId) {
        Matricula matricula = matriculaService.getMatricula(id);

        for (Resultado resultado : matricula.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {

                matricula.getResultados().remove(resultado);
                matricula.calculaMedia(matricula.getResultados());
                repository.save(matricula);
                return;
            }
        }

        throw new RegistroNaoEncontradoException("Não existe resultado com ID: " + resultadoId);
    }

    @Override
    public ResultadoResponseDTO obterResultadoPeloId(Long id, Long resultadoId) {
        Matricula matricula = matriculaService.getMatricula(id);

        for (Resultado resultado : matricula.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {

                Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (usuarioLogado.getRoles().contains("ALUNO")) {
                    boolean ehMatriculado = repository.existsByAlunoAndDisciplina(usuarioLogado.getAluno(), matricula.getDisciplina());

                    if (!ehMatriculado) throw new AccessDeniedException("Acesso Negado: Você não tem permissão para acessar esse resultado!");
                }
                return resultadoMapper.toDTO(resultado);
            }
        }

        throw new RegistroNaoEncontradoException("Não existe resultado com ID: " + resultadoId);
    }

    @Override
    @Transactional
    public Page<ResultadoResponseDTO> listar(Long id, int pagina, int tamanho, String sortDirection) {
        Matricula matricula = matriculaService.getMatricula(id);
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nota");

        List<Resultado> resultados = matricula.getResultados();

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuarioLogado.getRoles().contains("ALUNO")) {

            Aluno aluno = usuarioLogado.getAluno();
            List<Disciplina> disciplinasDoAluno = aluno.getDisciplinas();

            resultados = resultados
                    .stream()
                    .filter(resultado -> disciplinasDoAluno.contains(resultado.getExame().getDisciplina()))
                    .toList();
        }

        if (usuarioLogado.getRoles().contains("DOCENTE")) {

            Docente docente = usuarioLogado.getDocente();
            List<Disciplina> disciplinasDoDocente = docente.getDisciplinas();

            resultados = resultados
                    .stream()
                    .filter(resultado -> disciplinasDoDocente.contains(resultado.getExame().getDisciplina()))
                    .toList();
        }

        return new PageImpl<>(resultados, pageable, resultados.size()).map(resultadoMapper::toDTO);
    }
}
