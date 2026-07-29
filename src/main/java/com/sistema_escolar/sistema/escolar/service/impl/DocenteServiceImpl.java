package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.DocenteMapper;
import com.sistema_escolar.sistema.escolar.model.Departamento;
import com.sistema_escolar.sistema.escolar.model.Docente;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.DocenteRepository;
import com.sistema_escolar.sistema.escolar.repository.specs.DocenteSpecs;
import com.sistema_escolar.sistema.escolar.service.DepartamentoService;
import com.sistema_escolar.sistema.escolar.service.DocenteService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.DocenteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository repository;
    private final DepartamentoService departamentoService;
    private final DocenteMapper mapper;
    private final UsuarioService usuarioService;
    private final DocenteValidator validator;

    @Override
    public DocenteResponseDTO salvar(DocenteRequestDTO requestDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(requestDTO.getEmail());
        usuario.setUsername(requestDTO.getNome());
        usuario.setSenha(usuarioService.encriptaSenha(requestDTO.getSenha()));
        usuario.setEnabled(true);
        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);

        usuarioService.addRole(usuario, "DOCENTE");

        Docente docente = mapper.toEntity(requestDTO);
        Departamento departamento = departamentoService.getDepartamento(requestDTO.getDepartamentoId());
        docente.setRegistroInterno(UUID.randomUUID().toString().substring(10));
        docente.setDepartamento(departamento);
        docente.setUsuario(usuario); // associa usuário à docente.

        validator.validar(docente);
        return mapper.toDTO(repository.save(docente)); // salva docente.
    }

    @Override
    public DocenteResponseDTO atualizar(Long id, DocenteRequestDTO requestDTO) {
        Docente docente = getDocente(id);
        Usuario usuario = docente.getUsuario();
        usuario.setEmail(requestDTO.getEmail());
        usuario.setUsername(requestDTO.getNome());
        if(requestDTO.getSenha() != null) usuario.setSenha(usuarioService.encriptaSenha(requestDTO.getSenha()));


        docente.setCpf(requestDTO.getCpf());
        docente.setNome(requestDTO.getNome());
        docente.setEmail(requestDTO.getEmail());
        docente.setTelefone(requestDTO.getTelefone());
        docente.setFormacao(requestDTO.getFormacao());
        docente.setDataNascimento(requestDTO.getDataNascimento());
        docente.setSalario(requestDTO.getSalario());
        docente.setDepartamento(departamentoService.getDepartamento(requestDTO.getDepartamentoId()));

        validator.validar(docente);
        return mapper.toDTO(repository.save(docente)); // atualiza docente e usuário em cascata.
    }

    @Override
    public DocenteResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getDocente(id));
    }

    @Override
    public Page<DocenteResponseDTO> listar(int pagina, int tamanho, String sortDirection, String nome, Long departamentoId) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nome");
        Specification<Docente> specs = (root, query, cb) -> cb.conjunction();

        if (nome != null) specs = specs.and(DocenteSpecs.findByNome(nome));

        if (departamentoId != null) specs = specs.and(DocenteSpecs.findBydepartamentoId(departamentoId));

        return repository.findAll(specs, pageable).map(mapper::toDTO);
    }

    @Override
    public DocenteResponseDTO atualizarDocenteLogado(DocenteRequestDTO requestDTO) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        usuarioLogado.setEmail(requestDTO.getEmail());
        usuarioLogado.setUsername(requestDTO.getNome());
        if (requestDTO.getSenha() != null)  usuarioLogado.setSenha(usuarioService.encriptaSenha(requestDTO.getSenha()));


        Docente docente = repository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));

        docente.setCpf(requestDTO.getCpf());
        docente.setNome(requestDTO.getNome());
        docente.setEmail(requestDTO.getEmail());
        docente.setTelefone(requestDTO.getTelefone());
        docente.setFormacao(requestDTO.getFormacao());
        docente.setDataNascimento(requestDTO.getDataNascimento());
        docente.setSalario(requestDTO.getSalario());
        docente.setDepartamento(departamentoService.getDepartamento(requestDTO.getDepartamentoId()));

        validator.validar(docente);
        return mapper.toDTO(repository.save(docente));
    }

    @Override
    public DocenteResponseDTO obterDocenteLogado() {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();
        Docente docente = repository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado!"));

        return mapper.toDTO(docente);
    }

    @Override
    public void deletarPeloId(Long id) {
        Docente docente = getDocente(id);
        validator.validaDelecao(docente);
        repository.delete(docente);
    }

    @Override
    public Docente getDocente(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Docente não encontrado!"));
    }

    @Override
    public Long countDocente() {
        return repository.count();
    }
}
