package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CursoControllerDocs extends GenericController {
    @PostMapping
    ResponseEntity<CursoResponseDTO> salvar(@RequestBody @Valid CursoRequestDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<CursoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid CursoRequestDTO dto);

    @GetMapping("/{id}")
    ResponseEntity<CursoResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<CursoResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
