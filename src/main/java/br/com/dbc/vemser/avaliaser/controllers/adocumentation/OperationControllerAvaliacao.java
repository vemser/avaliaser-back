package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface OperationControllerAvaliacao {
    @Operation(summary = "Listar avaliações cadastrados", description = "Realiza a listagem de todas avaliações já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de avaliações realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<AvaliacaoDTO>> listarAvaliacoesPaginados(@RequestParam Integer page,
                                                                    @RequestParam Integer size);

    @Operation(summary = "Listagem de avaliações por aluno", description = "Realiza listagem de avaliações por aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listeagem por aluno com sucesso."),
            @ApiResponse(responseCode = "400", description = "Listagem por aluno falhou, verifique se o aluno inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<AvaliacaoDTO>> listarPorAlunoPaginado(@PathVariable("idAluno") Integer idAluno,
                                                                 @RequestParam Integer page,
                                                                 @RequestParam Integer size);

    @Operation(summary = "Atualiza dados de avaliação de acompanhamento por ID", description = "Realiza a busca de avaliação por ID, e realiza alteração de dados desta avaliação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação editada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AvaliacaoDTO> cadastrarAvaliacao(@RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Cadastrar uma avaliação", description = "Realiza o cadastro de dados da avaliação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AvaliacaoDTO> editandoAvaliacao(@PathVariable("idAvaliacao") Integer idAvaliacao,
                                                    @RequestBody EditarAvaliacaoDTO editarAvaliacaoDTO) throws RegraDeNegocioException;
}
