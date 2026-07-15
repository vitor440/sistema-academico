package com.sistema_escolar.sistema.escolar.service.impl;

import com.sistema_escolar.sistema.escolar.data.dto.MesAnoEMedia;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import com.sistema_escolar.sistema.escolar.mapper.ResultadoMapper;
import com.sistema_escolar.sistema.escolar.model.*;
import com.sistema_escolar.sistema.escolar.repository.ResultadoRepository;
import com.sistema_escolar.sistema.escolar.repository.specs.ResultadoSpecs;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import com.sistema_escolar.sistema.escolar.validator.ResultadoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public Page<ResultadoResponseDTO> listar(int pagina, int tamanho, String sortDirection, LocalDate data) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")? Sort.Direction.ASC: Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pagina, tamanho, direction, "nota");
        Usuario usuarioLogado = usuarioService.getUsuarioLogado();

        Specification<Resultado> specs = (root, query, cb) -> cb.conjunction();

        if(data != null) {
            specs = specs.and(ResultadoSpecs.greaterThanData(data));
        }

        if (usuarioLogado.getRoles().contains("ALUNO")) {
            Aluno aluno = usuarioLogado.getAluno();
            return resultadoRepository.obterResultadosDeAluno(aluno, pageable, specs).map(resultadoMapper::toDTO);
        }

        else if (usuarioLogado.getRoles().contains("DOCENTE")) {
            Docente docente = usuarioLogado.getDocente();
            return resultadoRepository.obterResultadosDaDisciplinaDoDocente(docente, pageable, specs).map(resultadoMapper::toDTO);
        }

        else {
            return resultadoRepository.findAll(specs, pageable).map(resultadoMapper::toDTO);
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

    @Override
    public List<MesAnoEMedia> mediaNotasUltimosQuatroMeses() {
        int anoAtual = LocalDate.now().getYear();
        int mesAtual = LocalDate.now().getMonth().getValue();

        LocalDateTime data = LocalDateTime.now().minusMonths(6);

        Long docenteId = usuarioService.getUsuarioLogado().getDocente().getId(); // obtem id do docente logado
        List<Object[]> objects = resultadoRepository.mediaNotasUltimosQuatroMeses(data, docenteId); // obtem a média de resultados de todos os exames do docente nos últimos 6 meses e agrupadas por més e ano


        List<MesAnoEMedia> dados = objects.stream().map(obj -> {
            return new MesAnoEMedia(mapMonth(String.valueOf(obj[0])), (Integer) obj[1], (Double) obj[2]);
        }).toList();

//        return dados;

        List<MesAnoEMedia> result = new ArrayList<MesAnoEMedia>();
        for (int i = mesAtual - 6; i <= mesAtual; i++) {

            if (existMes(dados, mapMonth(String.valueOf(i))) != null) {
                result.add(existMes(dados, mapMonth(String.valueOf(i))));
            }
            else {
                result.add(new MesAnoEMedia(mapMonth(String.valueOf(i)), anoAtual, 0.0));
            }
        }

        return result;
    }

    private String mapMonth(String m) {

        return switch (m) {

            case "1" -> "janeiro";
            case "2" -> "fevereiro";
            case "3" -> "março";
            case "4" -> "abril";
            case "5" -> "maio";
            case "6" -> "junho";
            case "7" -> "julho";
            case "8" -> "agosto";
            case "9" -> "setembro";
            case "10" -> "outubro";
            case "11" -> "novembro";
            case "12" -> "dezembro";
            default -> "";
        };
    }

    private MesAnoEMedia existMes(List<MesAnoEMedia> dados, String mes) {

        for (MesAnoEMedia dado : dados) {
            if (dado.mes().equalsIgnoreCase(mes)) {
                return dado;
            }
        }
        return null;
    }
}
