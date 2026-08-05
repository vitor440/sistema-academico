package com.sistema_escolar.sistema.escolar.controller;

import com.sistema_escolar.sistema.escolar.controller.docs.AlunoControllerDocs;
import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements AlunoControllerDocs {

    private final AlunoService service;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlunoResponseDTO> salvar(@RequestBody @Valid AlunoRequestDTO dto) {
        AlunoResponseDTO response = service.salvar(dto);
        URI location = getLocation(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO')")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AlunoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<AlunoResponseDTO> obterPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.obterPeloId(id));
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<Page<AlunoResponseDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "id-curso", required = false) Long idCurso,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "600") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(service.listar(nome, idCurso, pagina, tamanho, sortDirection));
    }


    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id) {
        service.deletarPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    @Override
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<AlunoResponseDTO> atualizarAlunoLogado(AlunoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizarAlunoLogado(dto));
    }

    @GetMapping("/me")
    @Override
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<AlunoResponseDTO> obterAlunoLogado() {
        return ResponseEntity.ok(service.obterAlunoLogado());
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> alunoCount() {
        return ResponseEntity.ok(service.countAluno());
    }


}
