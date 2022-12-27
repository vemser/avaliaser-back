package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadecomentariodto.AtividadeComentarioAvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadecomentariodto.AtividadeComentarioAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.comentariodto.ComentarioDTO;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface OperationControllerComentario {

    @Operation(summary = "Comentario de atividade", description = "Cadastrar comentario para atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de comentario com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/avaliar-comentar-atividade")
    public ResponseEntity<AtividadeComentarioAvaliacaoDTO> adicionarComentarioAvaliar(@RequestBody @Valid AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, Integer idAluno, Integer idAtividade) throws RegraDeNegocioException;

    @Operation(summary = "Listar comentários por id atividade", description = "Listar comentários por atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-comentario")
    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorAtividade(Integer idAtividade) throws RegraDeNegocioException;

    @Operation(summary = "Listar comentários por feedback positivo e negativo", description = "Listar comentários")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-feedback")
    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorFeedback(Situacao situacao);

    @Operation(summary = "Listar comentários do aluno", description = "Listar comentários de um aluno")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-comentarios-aluno")
    public ResponseEntity<PageDTO<ComentarioDTO>> listarComentariosAluno(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, Integer idAluno);

    @Operation(summary = "Adicionar feedback ao aluno", description = "Listar comentários de um aluno")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adicionar-feedback")
    public ResponseEntity<ComentarioDTO> adicionarFeedback(@RequestBody ComentarioDTO comentario, Integer idAluno, Situacao situacao) throws RegraDeNegocioException;
}
