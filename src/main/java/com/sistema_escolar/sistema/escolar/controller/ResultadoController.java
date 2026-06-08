package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.ResultadoControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ResultadoController implements ResultadoControllerDocs {

    private final ResultadoService service;

    @PostMapping("/matriculas/{id}/resultados")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<MatriculaResponseDTO> salvar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto) {
        return ResponseEntity.ok(service.salvarResultadoExame(id, dto));
    }

    @PutMapping("/resultados/{resultadoId}")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<MatriculaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizarResultadoExame(id, dto));
    }

    @DeleteMapping("/resultados/{resultadoId}")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        service.deletarResultadoExame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resultados/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<ResultadoResponseDTO> obterResultadoPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterResultadoPeloId(id));
    }

    @GetMapping("/resultados")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO', 'DOCENTE')")
    public ResponseEntity<Page<ResultadoResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection));
    }

    @GetMapping("/matriculas/{id}/resultados")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ResultadoResponseDTO>> listarPeloIdDaMatricula(Long id, int pagina, int tamanho, String sortDirection) {
        return ResponseEntity.ok(service.listarPeloIdDaMatricula(id, pagina, tamanho, sortDirection));
    }
}
