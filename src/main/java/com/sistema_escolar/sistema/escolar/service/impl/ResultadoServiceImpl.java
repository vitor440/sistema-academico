package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.ResultadoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final ResultadoMapper resultadoMapper;
    private final ResultadoValidator validator;
    private final ResultadoRepository resultadoRepository;
    private final ExameService exameService;
    private final MatriculaService matriculaService;
    private final UsuarioService usuarioService;



    @Override
    public ResultadoResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO) {
        Resultado resultado = resultadoMapper.toEntity(resultadoRequestDTO);
        Matricula matricula = matriculaService.getMatricula(id);
        Exame exame = exameService.getExame(resultadoRequestDTO.getExameId());
        validator.verificaSeExameEMatriculaSaoDaMesmaDisciplina(matricula, exame);

        Docente docente = exame.getDisciplina().getDocente();
        validator.validarDocenteLogado(docente);
        resultado.setExame(exame);

        resultado.setMatricula(matricula);
        validator.validar(resultado);

        matricula.addResultado(resultado);

        return resultadoMapper.toDTO(resultadoRepository.save(resultado));
    }

    @Override
    @Transactional
    public ResultadoResponseDTO atualizaNota(Long id, Double nota){
        Resultado resultado = getResultado(id);

        validator.validarDocenteLogado(resultado.getExame().getDisciplina().getDocente());

        resultado.setNota(nota);
        validator.validar(resultado);
        Resultado resultadoSalvo = resultadoRepository.save(resultado);

        Matricula matricula = resultado.getMatricula();
        matricula.calculaMedia(matricula.getResultados());

        return resultadoMapper.toDTO(resultadoSalvo);
    }

    @Override
    @Transactional
    public void deletarResultadoExame(Long id) {
        Resultado resultado = getResultado(id);

        validator.validarDocenteLogado(resultado.getExame().getDisciplina().getDocente());

        Matricula matricula = resultado.getMatricula();
        matricula.getResultados().remove(resultado);
        matricula.calculaMedia(matricula.getResultados());

        resultadoRepository.delete(resultado);
    }

    @Override
    public ResultadoResponseDTO obterResultadoPeloId(Long id) {
        Resultado resultado = getResultado(id);
        validator.validarAcesso(resultado);
        return resultadoMapper.toDTO(resultado);
    }

    @Override
    public Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nota");
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

    public Resultado getResultado(Long id) {
        return resultadoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Resultado não encontrado"));
    }
}
