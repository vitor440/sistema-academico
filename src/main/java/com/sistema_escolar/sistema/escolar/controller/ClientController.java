package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.ClientRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ClientResponseDTO;
import com.sistema_escolar.sistema.escolar.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ClientController implements com.sistema_escolar.sistema.escolar.controller.docs.ClientControllerDocs {

    private final ClientService service;

    @PostMapping
    @Override
    public ResponseEntity<ClientResponseDTO> salvar(@RequestBody @Valid ClientRequestDTO requestDTO) {
        ClientResponseDTO response = service.salvar(requestDTO);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ClientResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody ClientRequestDTO requestDTO) {
        return ResponseEntity.ok(service.atualizar(id, requestDTO));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ClientResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<ClientResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
