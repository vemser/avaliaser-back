package br.com.dbc.vemser.avaliaser.controllers.documentation;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

public interface OperationControllerAdministrador {

    @Operation(summary = "Listar Usuarios Cadastrados", description = "Realiza a listagem de todos os usuarios já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<UsuarioDTO>> listarUsuario(Integer page, Integer size) throws RegraDeNegocioException;

    @Operation(summary = "Busca usuario por Id", description = "Realiza busca de usuario cadastrado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuario não localizado, verifique se o ID inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Retorna usuário logado", description = "Retorna usuário que está logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Não foi possivel verificar o Usuario logado. Verifique se realizou o login.")
    })
    ResponseEntity<UsuarioDTO> uploadImagem(@PathVariable(name = "idUsuario") Integer idUsuario,
                                            @RequestPart(value = "file", required = false) MultipartFile file) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de Usuario por ID", description = "Realiza a busca de usuario por ID, e realiza alteração de dados deste usuario: nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<UsuarioDTO> atualizarUsuarioPorId(@PathVariable("idUsuario") Integer idUsuario, @RequestBody @Valid AtualizarUsuarioDTO atualizarUsuarioDTO) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um Usuario", description = "Realiza o cadastramento de dados do Usuario: nome, email e cargo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestParam Cargo cargo,
                                                @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Desativação de Usuario", description = "Realiza a exclusão lógica do Usuario, atualizando seu status no Banco de Dados para Ativo = 'N'!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario desativado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este usuario.")
    })
    ResponseEntity<Void> deletarUsuario(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException;

}
