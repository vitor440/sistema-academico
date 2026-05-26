package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.DisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DisciplinaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DisciplinaControllerDocs extends GenericController {
    @PostMapping
    ResponseEntity<DisciplinaResponseDTO> salvar(@RequestBody @Valid DisciplinaRequestDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<DisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid DisciplinaRequestDTO dto);

    @GetMapping("/{id}")
    ResponseEntity<DisciplinaResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<DisciplinaResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
