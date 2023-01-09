package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoFiltroDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface OperationControllerAcompanhamento {

//    @Operation(summary = "Listar acompanhamentos Cadastrados", description = "Realiza a listagem de todos os acompanhamentos já cadastrados no sistema.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
//            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
//    })
//    ResponseEntity<PageDTO<AcompanhamentoDTO>> listarAcompanhamentos(Integer page, Integer size) throws RegraDeNegocioException;

    @Operation(summary = "Busca acompanhamentos por Id", description = "Realiza busca de acompanhamentos cadastrado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Acompanhamento não localizado, verifique se o ID inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<AcompanhamentoDTO> buscarAcompanhamentosPorId(@PathVariable("idAcompanhamento") Integer idAcompanhamento) throws RegraDeNegocioException;

    @Operation(summary = "Busca acompanhamentos por titulo", description = "Realiza busca de acompanhamentos cadastrado por titulo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Acompanhamento não localizado."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    @GetMapping("/listar-acompanhamento-por-titulo")
    ResponseEntity<PageDTO<AcompanhamentoDTO>> listarAcompanhamentosAtivoPorTitulo(String titulo, Integer page, Integer size) throws RegraDeNegocioException;

    @Operation(summary = "Busca acompanhamentos por nome do programa", description = "Realiza busca de acompanhamentos cadastrado por nome do programa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Acompanhamento não localizado."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })

    @GetMapping("/listar-acompanhamento-por-nome-programa")
    ResponseEntity<PageDTO<AcompanhamentoFiltroDTO>> listarAcompanhamentosPorNomePrograma(Integer page, Integer size, String nome) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de acompanhamentos por Id", description = "Realiza a busca de acompanhamento por Id, e realiza alteração de dados deste acompanhamentos: titulo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acompanhamento editado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta ou com id inválido, tente de novo."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<AcompanhamentoDTO> update(@PathVariable("idAcompanhamento") Integer idAcompanhamento,
                                             @Valid @RequestBody AcompanhamentoCreateDTO createDTO) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um acompanhamento", description = "Realiza o cadastramento de dados do Acompanhamento: Titulo, Data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acompanhamento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta ou com id inválido, tente de novo."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<AcompanhamentoDTO> create(@Valid @RequestBody AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Desativação de acompanhamento", description = "Realiza a exclusão lógica do acompanhamento, atualizando seu status no Banco de Dados para Ativo = 'N'!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acompanhamento desativado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este aluno.")
    })
    @DeleteMapping(value = "/desativar/{idAcompanhamento}")
    ResponseEntity<Void> desativar(@PathVariable("idAcompanhamento") Integer idAcompanhamento) throws RegraDeNegocioException;

}
