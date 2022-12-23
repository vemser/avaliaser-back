package br.com.dbc.vemser.avaliaser.controllers.adocumentation.avaliaser;

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
                    @ApiResponse(responseCode = "201", description = "Programa Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ProgramaDTO> salvar(@Valid @RequestBody ProgramaCreateDTO programaCreate);

    @Operation(summary = "Listar pagina de programas", description = "Lista uma pagina de programas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Listar pagina de programas por nome", description = "Lista uma pagina de programas por nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/nome/{nome}")
    ResponseEntity<PageDTO<ProgramaDTO>> listarPorNome(Integer pagina, Integer tamanho, @PathVariable("nome") String nome);

    @Operation(summary = "Listar um programa por id", description = "Lista um programa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPrograma}")
    public ResponseEntity<PageDTO<ProgramaDTO>> listarPorId(Integer idPrograma) throws RegraDeNegocioException;

    @Operation(summary = "Editar programa", description = "Editar um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programa Editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPrograma}")
    ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaCreateDTO programaCreate,
                                       @PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;

    @Operation(summary = "Deletar programa", description = "Deleta o programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPrograma}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;
}
