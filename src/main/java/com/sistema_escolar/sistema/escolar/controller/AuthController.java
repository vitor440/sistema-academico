package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.AuthControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.UsuarioRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.UsuarioResponseDTO;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final UsuarioService service;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> salvarUsuarioAdmin(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = service.salvarUsuarioAdmin(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<UsuarioResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(pagina, tamanho, sortDirection));
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuarioAdmin(@RequestBody @Valid UsuarioRequestDTO dto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.atualizarUsuarioAdmin(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> obterDados() {
        return ResponseEntity.ok(service.obterDados());
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> usuarioCount() {
        return ResponseEntity.ok(service.countUsuario());
    }
}
