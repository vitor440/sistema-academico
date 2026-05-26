package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.AlunoDisciplinaMapper;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.AlunoDisciplina;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.repository.AlunoDisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final AlunoDisciplinaRepository repository;
    private final AlunoDisciplinaMapper mapper;
    private final ResultadoMapper resultadoMapper;
    private final ExameRepository exameRepository;



    @Override
    public AlunoDisciplinaResponseDTO salvarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO) {
        AlunoDisciplina alunoDisciplina = getAlunoDisciplina(id);
        Resultado resultado = resultadoMapper.toEntity(resultadoRequestDTO);
        resultado.setAluno(alunoDisciplina.getAluno());
        resultado.setExame(getExame(resultadoRequestDTO.getExameId()));

        resultado.setAlunoDisciplina(alunoDisciplina);
        alunoDisciplina.addResultado(resultado);
        return mapper.toDTO(repository.save(alunoDisciplina));
    }

    @Override
    public AlunoDisciplinaResponseDTO atualizarResultadoExame(Long id, ResultadoRequestDTO resultadoRequestDTO, Long resultadoId) {
        AlunoDisciplina alunoDisciplina = getAlunoDisciplina(id);

        for (Resultado resultado : alunoDisciplina.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {
                resultado.setNota(resultadoRequestDTO.getNota());
                resultado.setExame(getExame(resultadoRequestDTO.getExameId()));
                resultado.setAluno(alunoDisciplina.getAluno());
                resultado.setAlunoDisciplina(alunoDisciplina);

                alunoDisciplina.calculaMedia(alunoDisciplina.getResultados());
                return mapper.toDTO(repository.save(alunoDisciplina));
            }
        }

        throw new RegistroNaoEncontradoException("Não existe resultado com ID: " + resultadoId);
    }

    @Override
    public void deletarResultadoExame(Long id, Long resultadoId) {
        AlunoDisciplina alunoDisciplina = getAlunoDisciplina(id);

        for (Resultado resultado : alunoDisciplina.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {

                alunoDisciplina.getResultados().remove(resultado);
                alunoDisciplina.calculaMedia(alunoDisciplina.getResultados());
                repository.save(alunoDisciplina);
                return;
            }
        }

        throw new RegistroNaoEncontradoException("Não existe resultado com ID: " + resultadoId);
    }

    @Override
    public ResultadoResponseDTO obterResultadoPeloId(Long id, Long resultadoId) {
        AlunoDisciplina alunoDisciplina = getAlunoDisciplina(id);

        for (Resultado resultado : alunoDisciplina.getResultados()) {
            if (resultado.getId().equals(resultadoId)) {

                return resultadoMapper.toDTO(resultado);
            }
        }

        throw new RegistroNaoEncontradoException("Não existe resultado com ID: " + resultadoId);
    }


    private AlunoDisciplina getAlunoDisciplina(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado!"));
    }

    private Exame getExame(Long id) {
        return exameRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Exame não encontrado!"));
    }
}
