package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.data.dto.MesAnoEMedia;
import com.sistema_escolar.sistema.escolar.data.dto.request.ResultadoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.DepartamentoResponseDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.ResultadoResponseDTO;
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

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Resultados")
public interface ResultadoControllerDocs extends GenericController {

    @Operation(summary = "salvar resultado de exame", description = "Salva resultado de exame na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(description = "salvo com sucesso", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "nome do curso duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<ResultadoResponseDTO> salvar(@PathVariable("id") Long id, @RequestBody @Valid ResultadoRequestDTO dto);


    @Operation(summary = "atualizar resultado de exame", description = "Atualiza um resultado de exame pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "atualizado com sucesso", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "nome do curso duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "resultado não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<ResultadoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestParam(value = "nota") Double nota);


    @Operation(summary = "obter resultado de exame", description = "Obtém um resultado de exame pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "resultado deletado com sucesso", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "resultado não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<Void> deletar(@PathVariable("id") Long id);



    @Operation(summary = "obter resultado de exame", description = "Obtém um resultado de exame pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "resultado encontrado", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "resultado não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<ResultadoResponseDTO> obterResultadoPeloId(@PathVariable("id") Long id);

    ResponseEntity<Page<ResultadoResponseDTO>> listar(
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "semestre", required = false) Integer semestre,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "disciplinaId", required = false) Long disciplinaId);

    ResponseEntity<Page<ResultadoResponseDTO>> listarPeloIdDaMatricula(
            @PathVariable("id") Long id,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);

    ResponseEntity<List<MesAnoEMedia>> mediaNotasUltimosQuatroMeses();
}
