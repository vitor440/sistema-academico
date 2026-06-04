package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.data.dto.request.CursoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.CursoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.model.enums.Areas;
import com.sistema_escolar.sistema.escolar.model.enums.Periodo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cursos")
public interface CursoControllerDocs extends GenericController {

    @Operation(summary = "salvar curso", description = "Salva um curso na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(description = "salvo com sucesso", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "nome do curso duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<CursoResponseDTO> salvar(@RequestBody @Valid CursoRequestDTO dto);


    @Operation(summary = "atualizar curso", description = "Atualiza um curso pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "atualizado com sucesso", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "nome do curso duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "curso não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<CursoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid CursoRequestDTO dto);


    @Operation(summary = "obter curso", description = "Obtém um curso pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "curso encontrado", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "curso não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<CursoResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @Operation(summary = "listar curso", description = "Lista todos os curso")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Page<CursoResponseDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "area", required = false) Areas area,
            @RequestParam(value = "periodo", required = false, defaultValue = "0") Periodo periodo,
            @RequestParam(value = "nome-departamento", required = false, defaultValue = "0") String nomeDepartamento,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);


    @Operation(summary = "deletar curso", description = "Deleta um curso pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "curso deletado com sucesso", responseCode = "204",
                    content = @Content(schema = @Schema)),
            @ApiResponse(description = "curso não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
