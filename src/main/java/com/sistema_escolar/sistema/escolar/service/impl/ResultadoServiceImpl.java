package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Exame;
import com.sistema_escolar.sistema.escolar.model.Resultado;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.ExameRepository;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final ResultadoRepository repository;
    private final ResultadoMapper mapper;
    private final AlunoRepository alunoRepository;
    private final ExameRepository exameRepository;

    @Override
    public ResultadoResponseDTO salvar(ResultadoRequestDTO requestDTO) {
        Resultado resultado = mapper.toEntity(requestDTO);
//        Aluno aluno = getAluno(requestDTO.getAlunoId());
        Exame exame = getExame(requestDTO.getExameId());
//        resultado.setAluno(aluno);
        resultado.setExame(exame);

        return mapper.toDTO(repository.save(resultado));
    }

    @Override
    public ResultadoResponseDTO atualizar(Long id, ResultadoRequestDTO requestDTO) {
        Resultado resultado = getResultado(id);
        resultado.setNota(requestDTO.getNota());
//        resultado.setAluno(getAluno(requestDTO.getAlunoId()));
        resultado.setExame(getExame(requestDTO.getExameId()));
        resultado.setPeso(requestDTO.getPeso());

        return mapper.toDTO(repository.save(resultado));
    }



    @Override
    public ResultadoResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getResultado(id));
    }

    @Override
    public Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Resultado resultado = getResultado(id);
        repository.delete(resultado);
    }

    private Resultado getResultado(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Resultado de exame não encontrado!"));
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
