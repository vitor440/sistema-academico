package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.data.dto.request.DocenteRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DocenteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Docentes")
public interface DocenteControllerDocs extends GenericController {


    @Operation(summary = "salvar docente", description = "Salva um docente na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(description = "salvo com sucesso", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "registro interno/cpf/email duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<DocenteResponseDTO> salvar(@RequestBody @Valid DocenteRequestDTO dto);


    @Operation(summary = "atualizar docente", description = "Atualiza um docente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "atualizado com sucesso", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "registro interno/cpf/email duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "docente não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<DocenteResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid DocenteRequestDTO dto);


    @Operation(summary = "obter docente", description = "Obtém um docente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "docente encontrado", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "docente não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<DocenteResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @Operation(summary = "listar docentes", description = "Lista todos os docentes")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Page<DocenteResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "departamento-id", required = false) Long departamentoId);

    @Operation(summary = "deletar docente", description = "Deleta um docente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "docente deletado com sucesso", responseCode = "204",
                    content = @Content(schema = @Schema)),
            @ApiResponse(description = "docente não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);


    ResponseEntity<DocenteResponseDTO> atualizarDocenteLogado(DocenteRequestDTO dto);


    ResponseEntity<DocenteResponseDTO> obterDocenteLogado();

    ResponseEntity<Long> docenteCount();
}
