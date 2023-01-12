package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoFiltroDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface OperationControllerAluno {

    @Operation(summary = "Listar Alunos Cadastrados", description = "Realiza a listagem de todos os alunos já cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<PageDTO<AlunoDTO>> listarAlunos(@RequestParam(required = false) Integer idAluno,
                                                   @RequestParam(required = false) String nome,
                                                   @RequestParam(required = false) String email,
                                                   Integer page, Integer size) throws RegraDeNegocioException;
    @Operation(summary = "Listar alunos disponiveis para alocação e reserva", description = "Listar alunos disponiveis para alocacao!")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Alunos disponiveis listado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/disponiveis")
    ResponseEntity<PageDTO<AlunoDTO>> disponiveis(Integer pagina, Integer tamanho) throws RegraDeNegocioException;

    @Operation(summary = "Busca aluno por Id", description = "Realiza busca de aluno cadastrado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Aluno não localizado, verifique se o ID inserido está correto."),
            @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações.")
    })
    ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Integer idAluno) throws RegraDeNegocioException;
    @Operation(summary = "Listar alunos ativos por programa e trilha", description = "Listar alunos alunos ativos por programa e trilha!")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Alunos ativos listado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/alunos-ativos-por-programa/{idPrograma}")
     ResponseEntity<PageDTO<AlunoFiltroDTO>> listarAlunosAtivoPorProgramaTrilha(@RequestParam Integer page,
                                                                                @RequestParam Integer size,
                                                                                @PathVariable Integer idPrograma,
                                                                                @RequestParam List<Integer> idTrilhas) throws RegraDeNegocioException;


    @Operation(summary = "Atualiza dados de aluno por ID", description = "Realiza a busca de aluno por ID, e realiza alteração de dados deste aluno: nome, email, stack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AlunoDTO> atualizarAlunoPorId(@PathVariable Integer idAluno,
                                                 @Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Cadastrar um aluno", description = "Realiza o cadastramento de dados do aluno: nome, email e stack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Campo nulo, ou preenchido de forma incorreta, tente de novo.")
    })
    ResponseEntity<AlunoDTO> cadastrarAluno(@Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Desativação de aluno", description = "Realiza a exclusão lógica do aluno, atualizando seu status no Banco de Dados para Ativo = 'N'!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno desativado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este aluno.")
    })
    ResponseEntity<Void> desativarAluno(@PathVariable Integer idAluno) throws RegraDeNegocioException;

}
