package br.com.dbc.vemser.avaliaser.controllers.vemrankser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.rankdto.RankingDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/trilha")
public class TrilhaController {

    private final TrilhaService trilhaService;

    @Operation(summary = "Adicionar Trilha", description = "Realiza a adição de uma nova trilha.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha adicionada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações."),
                    @ApiResponse(responseCode = "500", description = "Não foi possivel verificar o Usuario logado. Verifique se realizou o login.")
            }
    )
    @PostMapping
    public ResponseEntity<TrilhaDTO> adicionarTrilha(@RequestBody @Valid TrilhaCreateDTO trilhaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando Trilha...");
        TrilhaDTO trilhaDTO = trilhaService.create(trilhaCreateDTO);
        log.info("Trilha Criada com sucesso!!");
        return new ResponseEntity<>(trilhaDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar Trilha", description = "Realiza a Atualização da Trilha no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha atualizada com sucesso !"),
                    @ApiResponse(responseCode = "403", description = "Você não possui credenciais para acessar essas informações."),
                    @ApiResponse(responseCode = "500", description = "Não foi possivel verificar o Usuario logado. Verifique se realizou o login.")
            }
    )
    @PutMapping("/update/{idTrilha}")
    public ResponseEntity<TrilhaDTO> updateTrilha(@PathVariable(name = "idTrilha") Integer idTrilha, @RequestBody TrilhaCreateDTO trilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.updateTrilha(idTrilha, trilha), HttpStatus.OK);

    }

    @Operation(summary = "Busca a lista de alunos na trilha pela pontuação", description = "Resgata a lista de alunos na trilha pela pontuacao no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso !"),
                    @ApiResponse(responseCode = "404", description = "Você não possui credenciais para acessar essas informações."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-ranking")
    public ResponseEntity<List<RankingDTO>> listranking(Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.rankingtrilha(idTrilha), HttpStatus.OK);
    }

    @Operation(summary = "Busca o nome da trilha ou lista", description = "Realiza a listagem do nome da trilha ou lista todas as trilhas do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso "),
                    @ApiResponse(responseCode = "404", description = "Você não possui credenciais para acessar essas informações."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-trilha-nome")
    public ResponseEntity<List<TrilhaDTO>> findTrilhaByNome(@RequestParam(required = false) String nome) {
        return new ResponseEntity<>(trilhaService.findTrilhaByNome(nome), HttpStatus.OK);
    }


    @Operation(summary = "Busca trilha pelo id", description = "Realiza a busca da trilha pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Você não possui credenciais para acessar essas informações."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-id-trilha")
    public ResponseEntity<TrilhaDTO> findById(Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.pegarIdTrilha(idTrilha), HttpStatus.OK);
    }

    @Operation(summary = "Busca a lista de trilha paginado", description = "Realiza a busca da lista de trilha paginado do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Você não possui credenciais para acessar essas informações."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-trilha-page")
    public ResponseEntity<PageDTO<TrilhaDTO>> listAllTrilhaPaginado(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho) {
        return new ResponseEntity<>(trilhaService.listarAllTrilhaPaginado(pagina, tamanho), HttpStatus.OK);
    }

    @Operation(summary = "Deletar uma trilha", description = "Realiza o exclusão logica de uma trilha do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Trilha desativada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem autorização para remover este usuario."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idTrilha}")
    public ResponseEntity<Void> deletarTrilha(@PathVariable(name = "idTrilha") Integer idTrilha) throws RegraDeNegocioException {
        trilhaService.delete(idTrilha);
        log.info("Trilha desativada com sucesso!");
        return ResponseEntity.noContent().build();
    }


}