package br.com.dbc.vemser.avaliaser.controllers.adocumentation.avaliaser;


import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface VagaInterfaceController {

    @Operation(summary = "Criar um registro de vaga.", description = "Cria um cadastro de vaga no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria vaga."),
                    @ApiResponse(responseCode = "200", description = "recupera dados do vaga logado no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<VagaDTO> salvar(@RequestBody @Valid VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos as vagas", description = "Listar todos as vagas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar todos as vagas do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<VagaDTO>> listar(Integer pagina, Integer tamanho) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos as vagas", description = "Listar todos as vagas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar todos as vagas do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/nome/{nome}")
    public ResponseEntity<PageDTO<VagaDTO>> listarPorNome(Integer pagina, Integer tamanho, @PathVariable("nome") String nome) throws RegraDeNegocioException;

    @Operation(summary = "Lista vaga por id", description = "Listar vaga por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar vaga do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idVaga}")
    public ResponseEntity<PageDTO<VagaDTO>> listarPoId(@PathVariable("idVaga") Integer idVaga) throws RegraDeNegocioException;

    @Operation(summary = "Editar a vaga por id", description = "Editar vaga por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Editar a vaga no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idVaga}")
    ResponseEntity<VagaDTO> editar(@PathVariable("idVaga") Integer idVaga, @RequestBody VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Deleta a vaga por id", description = "Deleta vaga por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta a vaga do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idVaga}")
    ResponseEntity<Void> deletar(@PathVariable("idVaga") Integer idVaga) throws RegraDeNegocioException;
}
