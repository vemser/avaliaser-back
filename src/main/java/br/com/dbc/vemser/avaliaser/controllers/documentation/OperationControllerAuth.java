package br.com.dbc.vemser.avaliaser.controllers.documentation;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
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

public interface OperationControllerAuth {

    @Operation(summary = "Listar Usuarios Cadastrados", description = "Realiza a listagem de todos os usuarios já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<UsuarioDTO>> listarUsuario(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina);

    @Operation(summary = "Busca usuario por Id", description = "Realiza busca de usuario cadastrado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuario não localizado, verifique se o ID inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer idUsuario) throws RegraDeNegocioException;

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

    @Operation(summary = "Upload de imagem para o Banco de Dados", description = "Registra, e também pode alterar, imagem para perfil do usuario no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload da imagem realizado com sucesso!"),
            @ApiResponse(responseCode = "415", description = "Tipo ou tamanho da imagem não permitidos, não são compatíveis com os padrões do sistema.")
    })
    ResponseEntity<UsuarioDTO> uploadImagem(MultipartFile file, Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados do Usuario Logado", description = "Realiza alteração de dados do usuario logado: nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<UsuarioDTO> atualizarUsuarioLogado(@RequestParam String nome) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de Usuario por ID", description = "Realiza a busca de usuario por ID, e realiza alteração de dados deste usuario: nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<UsuarioDTO> atualizarUsuarioPorId(AtualizarUsuarioDTO atualizarUsuarioDTO, Integer idUsuario) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um Usuario", description = "Realiza o cadastramento de dados do Usuario: nome, email e cargo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<UsuarioDTO> cadastrarUsuario(Cargo cargo,
                                                UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Esqueci minha Senha", description = "Caso seu email conste no nosso banco de dados de usuarios, " +
            "envia um email com link de acesso(e token de autenticação) para trocar a senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuperação de senha realiza com sucesso."),
            @ApiResponse(responseCode = "403", description = "Email incorreto! Não será possivel continuar com a recuperação de senha!")
    })
    ResponseEntity<Void> recuperarSenha(String email) throws RegraDeNegocioException;

    @Operation(summary = "Alteração de senha através de recuperação", description = "Realiza a mudança de senha do Usuario logado apos recuperação de Token por email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não foi identificado permissão para realizar esta recuperação.")
    })
    ResponseEntity<Void> alterarSenhaRecuperada(@RequestParam String senha) throws RegraDeNegocioException;

    @Operation(summary = "Atualização de senha do Usuario Logado", description = "Realiza a mudança de senha do Usuario logado após validar senha antiga!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso."),
            @ApiResponse(responseCode = "403", description = "Senha atualizada com sucesso.")
    })
    ResponseEntity<Void> atualizarSenhaUsuarioLogado(String senhaAntiga, String senhaNova) throws RegraDeNegocioException;


    @Operation(summary = "Desativação de Usuario", description = "Realiza a exclusão lógica do Usuario, atualizando seu status no Banco de Dados para Ativo = 'N'!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario desativado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este usuario.")
    })
    ResponseEntity<Void> deletarUsuario(@PathVariable Integer idUsuario) throws RegraDeNegocioException;

}
