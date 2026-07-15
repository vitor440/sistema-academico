package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.CursoControllerDocs;
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
import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController implements CursoControllerDocs {

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
            @RequestParam(value = "periodo", required = false) Periodo periodo,
            @RequestParam(value = "quantidade-periodos", required = false) Integer quantidadePeriodos,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        Page<CursoResponseDTO> response = service.listar(nome, area, periodo, quantidadePeriodos, pagina, tamanho, sortDirection);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/areas-count")
    @Override
    public ResponseEntity<List<Object[]>> quantidadeDeAreas() {
        return ResponseEntity.ok(service.quantidadeDeAreas());
    }

    @GetMapping("/alunos-curso")
    @Override
    public ResponseEntity<List<Object[]>> alunosPorCurso() {
        return ResponseEntity.ok(service.alunosPorCurso());
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> cursoCount() {
        return ResponseEntity.ok(service.countCurso());
    }
}
