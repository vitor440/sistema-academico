package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.HorarioDisciplinaControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.HorarioDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.HorarioDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.service.HorarioDisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class HorarioDisciplinaController implements HorarioDisciplinaControllerDocs {

    private final HorarioDisciplinaService service;

    @PostMapping("/disciplinas/{id}/horarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<DisciplinaResponseDTO> salvar(@PathVariable("id") Long id,
                                                        @RequestBody @Valid HorarioDisciplinaRequestDTO requestDTO) {
        DisciplinaResponseDTO responseDTO = service.salvar(id, requestDTO);
        URI location = getLocation(responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/horarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<HorarioDisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid HorarioDisciplinaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/horarios/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    @Override
    public ResponseEntity<HorarioDisciplinaResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping("/horarios")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    @Override
    public ResponseEntity<Page<HorarioDisciplinaResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "semestre", required = false) Integer semestre,
            @RequestParam(value = "ano", required = false) Integer ano) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection, semestre, ano));
    }



    @DeleteMapping("/horarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("matriculas/{id}/horarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Page<HorarioDisciplinaResponseDTO>> obterHorariosPeloIdDaDisciplina(
            @PathVariable("id") Long id,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.obterHorariosPeloIdDaDisciplina(id, pagina, tamanho, sortDirection));
    }


}
