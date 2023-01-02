package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface ProgramaInterfaceController {
    @Operation(summary = "Criar programa", description = "Cria um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programa Criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ProgramaDTO> create(@Valid @RequestBody ProgramaCreateDTO programaCreate) throws RegraDeNegocioException;

    @Operation(summary = "Listar pagina de programas", description = "Lista uma pagina de programas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer page, Integer size) throws RegraDeNegocioException;

    @Operation(summary = "Listar pagina de programas por nome", description = "Lista uma pagina de programas por nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/list-nome")
    ResponseEntity<PageDTO<ProgramaDTO>> listarPorNome(Integer page, Integer size,String nome) throws RegraDeNegocioException;

    @Operation(summary = "Buscar um programa por id", description = "Buscar um programa por id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programa resgatado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPrograma}")
    public ResponseEntity<ProgramaDTO> pegarPrograma(@PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;

    @Operation(summary = "Editar programa", description = "Editar um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programa Editado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPrograma}")
    ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaCreateDTO programaCreate,
                                       @PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;

    @Operation(summary = "Desativar programa", description = "Desativar o programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Desativado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPrograma}")
    public ResponseEntity<Void> desativar(@PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;
}
