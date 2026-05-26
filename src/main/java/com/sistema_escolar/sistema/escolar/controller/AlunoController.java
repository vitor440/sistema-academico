package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements com.sistema_escolar.sistema.escolar.controller.docs.AlunoControllerDocs {

    private final AlunoService service;

    @PostMapping
    @Override
    public ResponseEntity<AlunoResponseDTO> salvar(@RequestBody @Valid AlunoRequestDTO dto) {
        AlunoResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AlunoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<AlunoResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<AlunoResponseDTO>> listar(
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
