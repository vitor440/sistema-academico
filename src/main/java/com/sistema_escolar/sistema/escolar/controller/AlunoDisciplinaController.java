package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.service.AlunoDisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alunoDisciplinas")
@RequiredArgsConstructor
public class AlunoDisciplinaController implements GenericController{

    private final AlunoDisciplinaService service;

    @PostMapping
    public ResponseEntity<AlunoDisciplinaResponseDTO> salvar(@RequestBody @Valid AlunoDisciplinaRequestDTO dto) {
        AlunoDisciplinaResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/{id}/addResultado")
    public ResponseEntity<AlunoDisciplinaResponseDTO> salvar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto) {
        AlunoDisciplinaResponseDTO response = service.salvarResultadoExame(id, dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AlunoDisciplinaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDisciplinaResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    public ResponseEntity<Page<AlunoDisciplinaResponseDTO>> listar(
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
