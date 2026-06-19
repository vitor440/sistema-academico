package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.AlunoMapper;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Curso;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.AlunoRepository;
import com.sistema_escolar.sistema.escolar.repository.specs.AlunoSpecs;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import com.sistema_escolar.sistema.escolar.service.CursoService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.AlunoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {
    
    private final AlunoRepository repository;
    private final CursoService cursoService;
    private final AlunoMapper mapper;
    private final AlunoValidator validator;
    private final UsuarioService usuarioService;

    @Override
    @Transactional
    public AlunoResponseDTO salvar(AlunoRequestDTO requestDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(requestDTO.getEmail());
        usuario.setUsername(requestDTO.getNome());
        usuario.setSenha(usuarioService.encriptaSenha(requestDTO.getSenha()));
        usuario.setEnabled(true);
        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);

        usuarioService.addRole(usuario, "ALUNO");

        Aluno aluno = mapper.toEntity(requestDTO);
        Curso curso = cursoService.getCurso(requestDTO.getCursoId());
        aluno.setUsuario(usuario); // associa usuário à aluno.
        aluno.setCurso(curso);

        validator.validar(aluno);
        return mapper.toDTO(repository.save(aluno)); // salva aluno.
    }

    @Override
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO requestDTO) {
        Aluno aluno = getAluno(id);
        Usuario usuario = aluno.getUsuario();

        usuario.setEmail(requestDTO.getEmail());
        usuario.setUsername(requestDTO.getNome());
        usuario.setSenha(usuarioService.encriptaSenha(requestDTO.getSenha()));

        aluno.setCpf(requestDTO.getCpf());
        aluno.setNome(requestDTO.getNome());
        aluno.setEmail(requestDTO.getEmail());
        aluno.setMatricula(requestDTO.getMatricula());
        aluno.setTelefone(requestDTO.getTelefone());
        aluno.setDataNascimento(requestDTO.getDataNascimento());
        aluno.setCurso(cursoService.getCurso(requestDTO.getCursoId()));

        validator.validar(aluno);
        return mapper.toDTO(repository.save(aluno));
    }

    @Override
    public AlunoResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getAluno(id));
    }

    @Override
    public Page<AlunoResponseDTO> listar(String nome, Long idCurso, int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");

        Specification<Aluno> specs = (root, query, cb) -> cb.conjunction();

        if (nome != null) specs = specs.and(AlunoSpecs.findByName(nome));

        if(idCurso != null) specs = specs.and(AlunoSpecs.findByCurso(idCurso));

        return repository.findAll(specs, pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        Aluno aluno = getAluno(id);
        validator.validaDelecao(aluno);
        repository.delete(aluno);
    }

    @Override
    public AlunoResponseDTO atualizarAlunoLogado(AlunoRequestDTO requestDTO) {

        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        usuarioLogado.setEmail(requestDTO.getEmail());
        usuarioLogado.setUsername(requestDTO.getNome());
        usuarioLogado.setSenha(usuarioService.encriptaSenha(requestDTO.getSenha()));

        Aluno aluno = repository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));

        aluno.setCpf(requestDTO.getCpf());
        aluno.setNome(requestDTO.getNome());
        aluno.setEmail(requestDTO.getEmail());
        aluno.setMatricula(requestDTO.getMatricula());
        aluno.setTelefone(requestDTO.getTelefone());
        aluno.setDataNascimento(requestDTO.getDataNascimento());
        aluno.setCurso(cursoService.getCurso(requestDTO.getCursoId()));

        validator.validar(aluno);
        return mapper.toDTO(repository.save(aluno));
    }

    @Override
    public AlunoResponseDTO obterAlunoLogado() {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        Aluno aluno = repository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));

        return mapper.toDTO(aluno);
    }


    @Override
    public Aluno getAluno(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Aluno não encontrado!"));
    }
}
