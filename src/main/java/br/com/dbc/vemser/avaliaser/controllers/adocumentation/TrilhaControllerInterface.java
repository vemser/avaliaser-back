package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.rankdto.RankingDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface TrilhaControllerInterface {
    @Operation(summary = "Adicionar Trilha", description = "Adicionar uma nova trilha ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha adicionada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<TrilhaDTO> create(@RequestBody
                                     @Valid TrilhaCreateDTO trilhaCreateDTO);

    @Operation(summary = "Atualizar Trilha", description = "Atualizar Trilha no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<TrilhaDTO> update(@PathVariable(name = "idTrilha") Integer idTrilha,
                                     @Valid @RequestBody TrilhaCreateDTO trilha) throws RegraDeNegocioException;
    @Operation(summary = "Pega a lista de alunos na trilha pela pontuação", description = "Resgata a lista de alunos na trilha pela pontuacao no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<RankingDTO>> listRanking(Integer page,
                                                           Integer size,
                                                           Integer idTrilha) throws RegraDeNegocioException;
    @Operation(summary = "Pega o nome da trilha ou lista", description = "Resgata o nome da trilha ou lista do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<TrilhaDTO>> findTrilhaByNome(@RequestParam(required = false) String nome,
                                                        @RequestParam int page,
                                                        @RequestParam int size) throws RegraDeNegocioException;
    @Operation(summary = "Pega trilha pelo id", description = "Resgata a trilha pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<TrilhaDTO> findById(Integer idTrilha) throws RegraDeNegocioException;
    @Operation(summary = "Pega a lista de trilha paginado", description = "Resgata a lista de trilha paginado do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<TrilhaDTO>> listAllTrilhaPaginado(@RequestParam Integer page,
                                                                    @RequestParam Integer size) throws RegraDeNegocioException;
    @Operation(summary = "Desativa uma trilha", description = "Desativa uma trilha do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha desativada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso!"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
     ResponseEntity<Void> desativar(@PathVariable(name = "idTrilha") Integer idTrilha) throws RegraDeNegocioException;
}
