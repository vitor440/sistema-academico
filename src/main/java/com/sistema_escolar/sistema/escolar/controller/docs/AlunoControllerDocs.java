package com.sistema_escolar.sistema.escolar.controller.docs;

import com.sistema_escolar.sistema.escolar.controller.GenericController;
import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.data.dto.request.AlunoRequestDTO;
import com.sistema_escolar.sistema.escolar.data.dto.response.AlunoResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Alunos")
public interface AlunoControllerDocs extends GenericController {

    @Operation(summary = "salvar aluno", description = "Salva um aluno na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(description = "salvo com sucesso", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "nome/cpf/matricula duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<AlunoResponseDTO> salvar(@RequestBody @Valid AlunoRequestDTO dto);


    @Operation(summary = "atualizar aluno", description = "Atualiza um aluno pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "atualizado com sucesso", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AlunoResponseDTO.class))),
            @ApiResponse(description = "nome/cpf/matricula duplicado", responseCode = "409",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(description = "Aluno não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AlunoRequestDTO dto);


    @Operation(summary = "obter aluno", description = "Obtém um aluno pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "Aluno encontrado", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class))),
            @ApiResponse(description = "Aluno não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<AlunoResponseDTO> obterPeloId(@PathVariable("id") Long id);

    @Operation(summary = "listar alunos", description = "Lista todos os alunos")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Page<AlunoResponseDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "id-curso", required = false) Long idCurso,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "6") int tamanho,
            @RequestParam(value = "sort-direction", required = false, defaultValue = "DESC") String sortDirection);


    @Operation(summary = "deletar aluno", description = "Deleta um aluno pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(description = "aluno deletado com sucesso", responseCode = "204",
                    content = @Content(schema = @Schema)),
            @ApiResponse(description = "aluno não encontrado", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    ResponseEntity<Void> deletarPeloId(@PathVariable("id") Long id);

    ResponseEntity<AlunoResponseDTO> atualizarAlunoLogado(@RequestBody @Valid AlunoRequestDTO dto);

    ResponseEntity<AlunoResponseDTO> obterAlunoLogado();

}
