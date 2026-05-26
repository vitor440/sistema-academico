package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.ExameRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ExameResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ExameControllerDocs extends GenericController {
    @PostMapping
    ResponseEntity<ExameResponseDTO> salvar(@RequestBody @Valid ExameRequestDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<ExameResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid ExameRequestDTO dto);

    @GetMapping("/{id}")
    ResponseEntity<ExameResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<ExameResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
