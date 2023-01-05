package br.com.dbc.vemser.avaliaser.controllers.adocumentation;


import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoEditarDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface ReservaAlocacaoInterface {
    @Operation(summary = "Criar Reserva ou Alocação", description = "Cria uma Reserva e Alocação e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Reserva e Alocação  Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ReservaAlocacaoDTO> salvar(@Valid @RequestBody ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Editar Reserva alocação", description = "Editar uma Reserva alocação e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reserva alocação Editada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idReservaAlocacao}")
    ResponseEntity<ReservaAlocacaoDTO> editar(@Valid @RequestBody ReservaAlocacaoEditarDTO reservaAlocacaoEditarDTO,
                                              @PathVariable(name = "idReservaAlocacao") Integer idReservaAlocacao) throws RegraDeNegocioException;

    @Operation(summary = "Filtra pagina de Reserva alocação", description = "Filtra pagina Reserva alocação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reserva alocação Listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<ReservaAlocacaoDTO>> filtrar(Integer pagina, Integer tamanho, @RequestParam(name = "nomeAluno", required = false) String nomeAluno, @RequestParam(name = "nomeVaga", required = false) String nomeVaga) throws RegraDeNegocioException;



}
