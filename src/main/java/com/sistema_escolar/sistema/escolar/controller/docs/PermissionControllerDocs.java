package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.PermissionRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.PermissionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface PermissionControllerDocs extends GenericController {
    @PostMapping
    ResponseEntity<PermissionResponseDTO> salvar(@RequestBody @Valid PermissionRequestDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<PermissionResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid PermissionRequestDTO dto);

    @GetMapping("/{id}")
    ResponseEntity<PermissionResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<PermissionResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
