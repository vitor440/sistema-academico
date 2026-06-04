package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import com.sistema_escolar.sistema.escolar.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController implements com.sistema_escolar.sistema.escolar.controller.docs.CursoControllerDocs {

    private final CursoService service;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoResponseDTO> salvar(@RequestBody @Valid CursoRequestDTO dto) {
        CursoResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid CursoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<CursoResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<Page<CursoResponseDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "area", required = false) Areas area,
            @RequestParam(value = "periodo", required = false, defaultValue = "0") Periodo periodo,
            @RequestParam(value = "nome-departamento", required = false, defaultValue = "0") String nomeDepartamento,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(nome, area, periodo, nomeDepartamento, pagina, tamanho, sortDirection));
    }



    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
