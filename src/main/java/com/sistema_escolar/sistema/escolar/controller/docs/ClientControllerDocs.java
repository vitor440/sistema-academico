package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.request.ClientRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ClientResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "client")
public interface ClientControllerDocs extends GenericController {


    ResponseEntity<ClientResponseDTO> salvar(@RequestBody @Valid ClientRequestDTO requestDTO);

    ResponseEntity<ClientResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody ClientRequestDTO requestDTO);

    ResponseEntity<ClientResponseDTO> obterPeloId(@PathVariable("id") Long id);

    ResponseEntity<Page<ClientResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
