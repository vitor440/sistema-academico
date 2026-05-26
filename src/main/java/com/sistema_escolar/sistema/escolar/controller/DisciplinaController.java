package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController implements com.sistema_escolar.sistema.escolar.controller.docs.DisciplinaControllerDocs {

    private final DisciplinaService service;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DisciplinaResponseDTO> salvar(@RequestBody @Valid DisciplinaRequestDTO dto) {
        DisciplinaResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid DisciplinaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<DisciplinaResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<Page<DisciplinaResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection));
    }



    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
