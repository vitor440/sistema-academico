package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.DocenteControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import com.sistema_escolar.sistema.escolar.service.DocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/docentes")
@RequiredArgsConstructor
public class DocenteController implements DocenteControllerDocs {

    private final DocenteService service;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DocenteResponseDTO> salvar(@RequestBody @Valid DocenteRequestDTO dto) {
        DocenteResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<DocenteResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid DocenteRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<DocenteResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<Page<DocenteResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "600") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "departamento-id", required = false) Long departamentoId) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection, nome, departamentoId));
    }



    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<DocenteResponseDTO> atualizarDocenteLogado(@RequestBody @Valid DocenteRequestDTO dto) {
        return ResponseEntity.ok(service.atualizarDocenteLogado(dto));
    }

    @GetMapping("/me")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<DocenteResponseDTO> obterDocenteLogado() {
        return ResponseEntity.ok(service.obterDocenteLogado());
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> docenteCount() {
        return ResponseEntity.ok(service.countDocente());
    }
}
