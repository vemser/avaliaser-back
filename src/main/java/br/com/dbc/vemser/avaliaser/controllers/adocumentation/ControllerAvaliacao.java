package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface ControllerAvaliacao {
    @Operation(summary = "Listar avaliações cadastrados", description = "Realiza a listagem de todas avaliações já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de avaliações realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    @GetMapping("/listar-avaliacao-por-acompanhamento-aluno")
    ResponseEntity<PageDTO<AvaliacaoDTO>> filtrarAvaliacao(@RequestParam(required = false) Integer idAvaliacao,
                                                                  @RequestParam(required = false) TipoAvaliacao tipoAvaliacao,
                                                                  @RequestParam(required = false) String tituloAcompanhamento,
                                                                  @RequestParam(required = false) String nomeAluno,
                                                                  Integer pagina,
                                                                  Integer tamanho) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar uma avaliação", description = "Realiza o cadastro de dados da avaliação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AvaliacaoDTO> create(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de avaliação de acompanhamento por Id", description = "Realiza a busca de avaliação por Id, e realiza alteração de dados desta avaliação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação editada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AvaliacaoDTO> update(@PathVariable("idAvaliacao") Integer idAvaliacao,
                                               @Valid @RequestBody EditarAvaliacaoDTO editarAvaliacaoDTO) throws RegraDeNegocioException;
    @Operation(summary = "Desativação de avaliação", description = "Realiza a exclusão lógica do avaliação, atualizando seu status no Banco de Dados para Ativo = 'N'!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta ou com id inválido, tente de novo."),
            @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este aluno.")
    })
    @DeleteMapping(value = "/desativar/{idAvaliacao}")
    ResponseEntity<Void> desativar(@PathVariable("idAvaliacaoo") Integer idAvaliacao) throws RegraDeNegocioException;
}
