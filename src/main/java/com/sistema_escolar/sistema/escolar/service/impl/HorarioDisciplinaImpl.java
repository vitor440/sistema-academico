package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.request.HorarioDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.HorarioDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.DisciplinaMapper;
import com.sistema_escolar.sistema.escolar.mapper.HorarioDisciplinaMapper;
import com.sistema_escolar.sistema.escolar.model.Aluno;
import com.sistema_escolar.sistema.escolar.model.Disciplina;
import com.sistema_escolar.sistema.escolar.model.HorarioDisciplina;
import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.repository.DisciplinaRepository;
import com.sistema_escolar.sistema.escolar.repository.HorarioDisciplinaRepository;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import com.sistema_escolar.sistema.escolar.service.HorarioDisciplinaService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HorarioDisciplinaImpl implements HorarioDisciplinaService {

    private final HorarioDisciplinaRepository repository;
    private final HorarioDisciplinaMapper mapper;
    private final DisciplinaService disciplinaService;
    private final DisciplinaMapper disciplinaMapper;
    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioService usuarioService;

    @Override
    public DisciplinaResponseDTO salvar(Long id, HorarioDisciplinaRequestDTO requestDTO) {
        Disciplina disciplina = disciplinaService.getDisciplina(id);
        HorarioDisciplina horarioDisciplina = mapper.toEntity(requestDTO);
        horarioDisciplina.setDisciplina(disciplina);
        disciplina.getHorarios().add(horarioDisciplina);

        return disciplinaMapper.toDTO(disciplinaRepository.save(disciplina));
    }

    @Override
    public HorarioDisciplinaResponseDTO atualizar(Long id, HorarioDisciplinaRequestDTO requestDTO) {
        HorarioDisciplina horarioDisciplina = getHorarioDisciplina(id);

        horarioDisciplina.setHorario(requestDTO.getHorario());
        horarioDisciplina.setDiaSemana(requestDTO.getDiaSemana());
        horarioDisciplina.setPeriodo(requestDTO.getPeriodo());

        return mapper.toDTO(repository.save(horarioDisciplina));
    }

    @Override
    public HorarioDisciplinaResponseDTO obterPeloId(Long id) {
        return mapper.toDTO(getHorarioDisciplina(id));
    }

    @Override
    public Page<HorarioDisciplinaResponseDTO> listar(int pagina, int tamanho, String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "horario");

        Usuario usuario = usuarioService.getUsuarioLogado();
        if(usuario.getRoles().contains("ALUNO")) {
            return repository.obterHorariosDoAlunoPaginado(usuario.getAluno(), pageable).map(mapper::toDTO);
        }

        if (usuario.getRoles().contains("DOCENTE")) {
            return repository.obterHorariosDoDocente(usuario.getDocente(), pageable).map(mapper::toDTO);
        }

        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public void deletarPeloId(Long id) {
        HorarioDisciplina horarioDisciplina = getHorarioDisciplina(id);
        repository.delete(horarioDisciplina);
    }

    @Override
    public HorarioDisciplina getHorarioDisciplina(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado!"));
    }

    @Override
    public Page<HorarioDisciplinaResponseDTO> obterHorariosPeloIdDaDisciplina(Long id, int pagina, int tamanho, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "horario");
        Disciplina disciplina = disciplinaService.getDisciplina(id);

        return repository.findByDisciplina(disciplina, pageable).map(mapper::toDTO);
    }

    @Override
    public Page<HorarioDisciplinaResponseDTO> obterHorariosAlunoPeloSemestreEAno(Long alunoId, Integer semestre,
                                                                                 Integer ano,
                                                                                 Integer pagina,
                                                                                 Integer tamanho,
                                                                                 String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "horario");

        return repository.obterHorariosAlunoPorSemestreEAno(alunoId, semestre, ano, pageable)
                .map(mapper::toDTO);
    }
}
