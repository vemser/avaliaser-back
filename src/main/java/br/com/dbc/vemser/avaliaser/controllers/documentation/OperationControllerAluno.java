package br.com.dbc.vemser.avaliaser.controllers.documentation;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.AtualizarUsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface OperationControllerAluno {

    @Operation(summary = "Listar Alunos Cadastrados", description = "Realiza a listagem de todos os alunos já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<AlunoDTO>> listarAlunos(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina);

    @Operation(summary = "Busca aluno por Id", description = "Realiza busca de aluno cadastrado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Aluno não localizado, verifique se o ID inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Integer idAluno) throws RegraDeNegocioException;


    @Operation(summary = "Upload de imagem para o Banco de Dados", description = "Registra, e também pode alterar, imagem para perfil do aluno no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload da imagem realizado com sucesso!"),
            @ApiResponse(responseCode = "415", description = "Tipo ou tamanho da imagem não permitidos, não são compatíveis com os padrões do sistema.")
    })
    ResponseEntity<AlunoDTO> uploadImagem(MultipartFile file, Integer idAluno) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza dados de aluno por ID", description = "Realiza a busca de aluno por ID, e realiza alteração de dados deste aluno: nome, email, stack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AlunoDTO> atualizarAlunoPorId(AlunoCreateDTO alunoCreateDTO, Integer idAluno) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um aluno", description = "Realiza o cadastramento de dados do aluno: nome, email e stack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AlunoDTO> cadastrarAluno(Stack stack,
                                            AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Desativação de aluno", description = "Realiza a exclusão lógica do aluno, atualizando seu status no Banco de Dados para Ativo = 'N'!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno desativado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este aluno.")
    })
    ResponseEntity<Void> desativarAluno(@PathVariable Integer idUsuario) throws RegraDeNegocioException;

}
