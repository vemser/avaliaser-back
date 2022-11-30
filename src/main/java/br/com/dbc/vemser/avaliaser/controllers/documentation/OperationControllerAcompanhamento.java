package br.com.dbc.vemser.avaliaser.controllers.documentation;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.AtualizarUsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

public interface OperationControllerAcompanhamento {

    @Operation(summary = "Listar acompanhamentos Cadastrados", description = "Realiza a listagem de todos os acompanhamentos já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<AcompanhamentoDTO>> listarAcompanhamentos(Integer page, Integer size);

    @Operation(summary = "Busca acompanhamentos por Id", description = "Realiza busca de acompanhamentos cadastrado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Acompanhamento não localizado, verifique se o ID inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<AcompanhamentoDTO> buscarAcompanhamentosPorId(@RequestParam("idAcompanhamento") Integer idAcompanhamento) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de acompanhamentos por ID", description = "Realiza a busca de acompanhamento por ID, e realiza alteração de dados deste acompanhamentos: titulo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acompanhamento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AcompanhamentoDTO> editarAcompanhamento(@RequestParam("idAcompanhamento") Integer idAcompanhamento,
                                                           @Valid @RequestBody EditarAcompanhamentoDTO editarAcompanhamentoDTO) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um acompanhamento", description = "Realiza o cadastramento de dados do Acompanhamento: Titulo, Data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acompanhamento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AcompanhamentoDTO> cadastrarAcompanhamento( @Valid @RequestBody AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException;




}
