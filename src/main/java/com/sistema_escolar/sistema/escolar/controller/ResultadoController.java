package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import com.sistema_escolar.sistema.escolar.service.ResultadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunoDisciplinas")
@RequiredArgsConstructor
public class ResultadoController implements com.sistema_escolar.sistema.escolar.controller.docs.ResultadoControllerDocs {

    private final ResultadoService service;

    @PostMapping("/{id}/resultados")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<AlunoDisciplinaResponseDTO> salvar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto) {
        AlunoDisciplinaResponseDTO response = service.salvarResultadoExame(id, dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/resultados/{resultadoId}")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<AlunoDisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto,
                                                                @PathVariable("resultadoId") Long resultadoId) {
        AlunoDisciplinaResponseDTO response = service.atualizarResultadoExame(id, dto, resultadoId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/resultados/{resultadoId}")
    @Override
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id, @PathVariable("resultadoId") Long resultadoId) {
        service.deletarResultadoExame(id, resultadoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/resultados/{resultadoId}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE', 'ALUNO')")
    public ResponseEntity<ResultadoResponseDTO> obterResultadoPeloId(@PathVariable("id") Long id, @PathVariable("resultadoId") Long resultadoId) {
        ResultadoResponseDTO response = service.obterResultadoPeloId(id, resultadoId);
        return ResponseEntity.ok(response);
    }


}
