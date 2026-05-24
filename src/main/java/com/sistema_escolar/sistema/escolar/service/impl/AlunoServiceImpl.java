package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.AlunoMapper;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.CursoRepository;
import com.sistema_escolar.sistema.escolar.repository.UsuarioRepository;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import com.sistema_escolar.sistema.escolar.validator.AlunoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {
    
    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final AlunoMapper mapper;
    private final AlunoValidator validator;
    
    @Override
    public AlunoResponseDTO salvar(AlunoRequestDTO requestDTO) {
        Aluno aluno = mapper.toEntity(requestDTO);
        Usuario usuario = getUsuario(requestDTO.getUsuarioId());
        Curso curso = getCurso(requestDTO.getCursoId());
        aluno.setUsuario(usuario);
        aluno.setCurso(curso);

        validator.validar(aluno);
        return mapper.toDTO(repository.save(aluno));
    }

    @Override
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO requestDTO) {
        Aluno aluno = getAluno(id);
        aluno.setCpf(requestDTO.getCpf());
        aluno.setNome(requestDTO.getNome());
        aluno.setEmail(requestDTO.getEmail());
        aluno.setMatricula(requestDTO.getMatricula());
        aluno.setTelefone(requestDTO.getTelefone());
        aluno.setCursoPeriodo(requestDTO.getCursoPeriodo());
        aluno.setDataNascimento(requestDTO.getDataNascimento());
        aluno.setCurso(getCurso(requestDTO.getCursoId()));
        aluno.setUsuario(getUsuario(requestDTO.getUsuarioId()));

        validator.validar(aluno);
        return mapper.toDTO(repository.save(aluno));
    }

    @Override
    public AlunoResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getAluno(id));
    }

    @Override
    public Page<AlunoResponseDTO> listar(int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Aluno aluno = getAluno(id);
        repository.delete(aluno);
    }

    private Aluno getAluno(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Aluno não encontrado!"));
    }

    private Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuario não encontrado!"));
    }

    private Curso getCurso(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Curso não encontrado!"));
    }
}
