package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.MatriculaMapper;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.Matricula;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.MatriculaRepository;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import com.sistema_escolar.sistema.escolar.validator.ResultadoValidator;
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
        Matricula matricula = matriculaService.getMatricula(id);
        Resultado resultado = resultadoMapper.toEntity(resultadoRequestDTO);
        resultado.setAluno(matricula.getAluno());
        resultado.setExame(exameService.getExame(resultadoRequestDTO.getExameId()));
        validator.validar(resultado);

        resultado.setMatricula(matricula);
        matricula.addResultado(resultado);

        return mapper.toDTO(repository.save(matricula));
    }

    @Override
    public MatriculaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO, Long resultadoId) {
        Matricula matricula = matriculaService.getMatricula(id);

        for (Resultado resultado : matricula.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {
                resultado.setNota(resultadoRequestDTO.getNota());
                resultado.setExame(exameService.getExame(resultadoRequestDTO.getExameId()));
                resultado.setAluno(matricula.getAluno());
                validator.validar(resultado);

                resultado.setMatricula(matricula);
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
    public Page<ResultadoResponseDTO> listar(Long id, int pagina, int tamanho, String sortDirection) {
        Matricula matricula = matriculaService.getMatricula(id);
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nota");

        List<Resultado> resultados = matricula.getResultados();

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuarioLogado.getRoles().contains("ALUNO")) {
            resultados = resultados.
                    stream()
                    .filter(resultado -> usuarioLogado.getAluno().getDisciplinas().contains(resultado.getExame().getDisciplina()))
                    .toList();
        }

        return new PageImpl<>(resultados, pageable, resultados.size()).map(resultadoMapper::toDTO);
    }
}
