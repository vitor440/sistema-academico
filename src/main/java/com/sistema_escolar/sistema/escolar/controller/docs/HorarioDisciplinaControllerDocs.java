package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.HorarioDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.HorarioDisciplinaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface HorarioDisciplinaControllerDocs extends GenericController {
    ResponseEntity<DisciplinaResponseDTO> salvar(@PathVariable("id") Long id,
                                                 @RequestBody @Valid HorarioDisciplinaRequestDTO requestDTO);


    ResponseEntity<HorarioDisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid HorarioDisciplinaRequestDTO dto);


    ResponseEntity<HorarioDisciplinaResponseDTO> obterPeloId(@PathVariable("id") Long id);


    ResponseEntity<Page<HorarioDisciplinaResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);


    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);


    ResponseEntity<Page<HorarioDisciplinaResponseDTO>> obterHorariosPeloIdDaDisciplina(
            @PathVariable("id") Long id,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    ResponseEntity<Page<HorarioDisciplinaResponseDTO>> obterHorarioAlunoPeloSemestreEAno(
            @RequestParam(value = "aluno-id") Long alunoId,
            @RequestParam(value = "semestre") Integer semestre,
            @RequestParam(value = "ano") Integer ano,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);
}
