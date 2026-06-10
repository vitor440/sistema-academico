package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.MatriculaControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.MatriculaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.MatriculaResponseDTO;
import com.sistema_escolar.sistema.escolar.model.enums.StatusSolicitacao;
import com.sistema_escolar.sistema.escolar.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MatriculaController implements MatriculaControllerDocs {

    private final MatriculaService service;

    @PostMapping("/matriculas")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO')")
    public ResponseEntity<MatriculaResponseDTO> salvar(@RequestBody @Valid MatriculaRequestDTO dto) {
        MatriculaResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/matriculas/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO', 'DOCENTE')")
    public ResponseEntity<MatriculaResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping("/matriculas")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO', 'DOCENTE')")
    public ResponseEntity<Page<MatriculaResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection));
    }

    @DeleteMapping("/matriculas/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/matriculas/{id}/notaFinal")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> modificaNotaFinal(@PathVariable("id") Long id, @RequestParam(value = "nota-final") Double nota) {
        service.modificaNotaFinal(id, nota);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/matriculas/{id}/statusSolicitacao")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> modificaNotaFinal(@PathVariable("id") Long id, @RequestParam(value = "status-solicitacao") StatusSolicitacao statusSolicitacao) {
        service.modificaStatusSolicitacao(id, statusSolicitacao);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/matriculas/{id}/efetivarHistorico")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<MatriculaResponseDTO> efetivarHistorico(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.efetivarHistorico(id));
    }

    @PatchMapping("/matriculas/{id}/acrescentaFaltas")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> acrescentaFaltas(@PathVariable("id") Long id,
                                                                  @RequestParam(value = "faltas") int faltas) {
        service.acrescentaFaltas(id, faltas);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/matriculas/{id}/decrementaFaltas")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> decrementaFaltas(@PathVariable("id") Long id,
                                                  @RequestParam(value = "faltas") int faltas) {
        service.decrementaFaltas(id, faltas);
        return ResponseEntity.noContent().build();
    }
}
