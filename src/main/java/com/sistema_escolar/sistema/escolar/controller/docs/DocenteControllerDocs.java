package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DocenteControllerDocs extends GenericController {
    @PostMapping
    ResponseEntity<DocenteResponseDTO> salvar(@RequestBody @Valid DocenteRequestDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<DocenteResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid DocenteRequestDTO dto);

    @GetMapping("/{id}")
    ResponseEntity<DocenteResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<DocenteResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
