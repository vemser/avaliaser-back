package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface OperationControllerFeedBack {

    @Operation(summary = "Listar feedbacks Cadastrados", description = "Realiza a listagem de todos os feedbacks já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackPaginado(@RequestParam(required = false)Integer idFeedback,
                                                                @RequestParam(required = false)Integer idAluno,
                                                                @RequestParam(required = false)String nome,
                                                                Integer page, Integer size) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de feedbacks por ID", description = "Realiza a busca de feedbacks por ID, e realiza alteração de dados deste acompanhamentos: idAluno,Descrição,Status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback editado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, ou com id inválido, tente de novo."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<FeedBackDTO> editarFeedBack(@PathVariable("idFeedBack") Integer idFeedBack,
                                               @Valid @RequestBody EditarFeedBackDTO editarFeedBackDTO) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um acompanhamento", description = "Realiza o cadastramento de dados do Feedbacks: idAluno,Descrição,Status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedbacks cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, ou com id inválido, tente de novo."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<FeedBackDTO> cadastrarFeedBack(@Valid @RequestBody FeedBackCreateDTO feedBackCreateDTO) throws RegraDeNegocioException;


}
