package com.sistema_escolar.sistema.escolar.controller;


import com.sistema_escolar.sistema.escolar.data.dto.request.PermissionRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.PermissionResponseDTO;
import com.sistema_escolar.sistema.escolar.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/permissoes")
@RequiredArgsConstructor
public class PermissionController implements GenericController{

    private final PermissionService service;

    @PostMapping
    public ResponseEntity<PermissionResponseDTO> salvar(@RequestBody @Valid PermissionRequestDTO dto) {
        PermissionResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid PermissionRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    public ResponseEntity<Page<PermissionResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }


}
