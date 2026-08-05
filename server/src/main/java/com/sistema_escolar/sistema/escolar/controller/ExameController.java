package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.ExameControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import com.sistema_escolar.sistema.escolar.model.enums.StatusExame;
import com.sistema_escolar.sistema.escolar.model.enums.TipoExame;
import com.sistema_escolar.sistema.escolar.service.ExameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController implements ExameControllerDocs {

    private final ExameService service;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<ExameResponseDTO> salvar(@RequestBody @Valid ExameRequestDTO dto) {
        ExameResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<ExameResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid ExameRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<ExameResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<Page<ExameResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
        @RequestParam(value = "tamanho", required = false, defaultValue = "600") int tamanho,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "data", required = false) LocalDate data,
            @RequestParam(value = "semestre", required = false) Integer semestre,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "disciplinaId", required = false) Long disciplinaId,
            @RequestParam(value = "tipo", required = false) TipoExame tipo,
            @RequestParam(value = "status", required = false) StatusExame status) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection, data, semestre, ano, disciplinaId, tipo, status));
    }



    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id, @RequestParam(value = "status") StatusExame status) {
        service.atualizaStatusExame(id, status);
        return ResponseEntity.noContent().build();
    }



    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> exameCount() {
        return ResponseEntity.ok(service.countExame());
    }
}
