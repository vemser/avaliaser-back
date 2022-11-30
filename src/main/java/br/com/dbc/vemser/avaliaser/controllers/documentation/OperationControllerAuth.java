package br.com.dbc.vemser.avaliaser.controllers.documentation;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.AtualizarUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.TrocarSenhaUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface OperationControllerAuth {
    @Operation(summary = "Retorna usuário logado", description = "Retorna usuário que está logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Não foi possivel verificar o Usuario logado. Verifique se realizou o login.")
    })
    ResponseEntity<UsuarioLogadoDTO> getUsuarioLogado() throws RegraDeNegocioException;

    @Operation(summary = "Login do Usuario.", description = "Realiza o seu login com email e senha, liberando Token para Autenticação!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Email ou senha incorretos. Login não concluído.")
    })
    ResponseEntity<String> loginUsuario(@RequestBody LoginDTO loginDTO) throws RegraDeNegocioException;
    @Operation(summary = "Atualiza dados do Usuario Logado", description = "Realiza alteração de dados do usuario logado: nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<UsuarioDTO> atualizarUsuarioLogado(@Valid @RequestBody AtualizarUsuarioLogadoDTO nome) throws RegraDeNegocioException;
    @Operation(summary = "Esqueci minha Senha", description = "Caso seu email conste no nosso banco de dados de usuarios, " +
            "envia um email com link de acesso(e token de autenticação) para trocar a senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuperação de senha realiza com sucesso."),
            @ApiResponse(responseCode = "403", description = "Email incorreto! Não será possivel continuar com a recuperação de senha!")
    })
    ResponseEntity<Void> recuperarSenha(@RequestParam String email) throws RegraDeNegocioException;

    @Operation(summary = "Alteração de senha através de recuperação", description = "Realiza a mudança de senha do Usuario logado apos recuperação de Token por email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não foi identificado permissão para realizar esta recuperação.")
    })
    ResponseEntity<Void> alterarSenhaRecuperada(@RequestBody String senhaAntiga) throws RegraDeNegocioException;

    @Operation(summary = "Atualização de senha do Usuario Logado", description = "Realiza a mudança de senha do Usuario logado após validar senha antiga!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Senha atual informada está incorreta! \nNão é possível alterar senha."),
            @ApiResponse(responseCode = "403", description = "Não foi identificado permissão para realizar esta operação.")
    })
    ResponseEntity<Void> atualizarSenhaUsuarioLogado(@RequestBody @Valid TrocarSenhaUsuarioLogadoDTO senhas) throws RegraDeNegocioException;

}
