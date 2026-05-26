package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ResultadoControllerDocs extends GenericController {
    @PostMapping("/{id}/resultados")
    ResponseEntity<AlunoDisciplinaResponseDTO> salvar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto);

    @PutMapping("/{id}/resultados/{resultadoId}")
    ResponseEntity<AlunoDisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto,
                                                         @PathVariable("resultadoId") Long resultadoId);

    @DeleteMapping("/{id}/resultados/{resultadoId}")
    ResponseEntity<Void> deletar(@PathVariable("id") Long id, @PathVariable("resultadoId") Long resultadoId);

    @GetMapping("/{id}/resultados/{resultadoId}")
    ResponseEntity<ResultadoResponseDTO> obterResultadoPeloId(@PathVariable("id") Long id, @PathVariable("resultadoId") Long resultadoId);
}
