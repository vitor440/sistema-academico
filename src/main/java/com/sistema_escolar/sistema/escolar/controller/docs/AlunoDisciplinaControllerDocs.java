package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoDisciplinaRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoDisciplinaResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
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

@Tag(name = "aluno-disciplina")
public interface AlunoDisciplinaControllerDocs extends GenericController {

    @Operation(summary = "registrar matrícula de aluno em uma disciplina", description = "Adiciona matrícula de um aluno em uma disciplina na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(description = "salvo com sucesso", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "registro com aluno e disciplina duplicado / vagas insuficientes / conflito de horários", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<AlunoDisciplinaResponseDTO> salvar(@RequestBody @Valid AlunoDisciplinaRequestDTO dto);


    @Operation(summary = "atualizar matricula de aluno em uma disciplina", description = "atualiza a matrícula de um aluno em uma disciplina através do ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "atualizado com sucesso", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "registro com aluno e disciplina duplicado / vagas insuficientes / conflito de horários", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "matricula não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<AlunoDisciplinaResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AlunoDisciplinaRequestDTO dto);


    @Operation(summary = "obter departamento", description = "Obtém um departamento pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "matrícula encontrada", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "matricula não encontrada", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<AlunoDisciplinaResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @Operation(summary = "listar matriculas", description = "Lista todos as matriculas")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Page<AlunoDisciplinaResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);


    @Operation(summary = "deletar matrícula", description = "Deleta uma matrícula pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "matrícula deletada com sucesso", responseCode = "204",
                    content = @Content(schema = @Schema)),
            @ApiResponse(description = "matrícula não encontrada", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);
}
