package br.com.dbc.vemser.avaliaser.controllers.adocumentation;

import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface ClienteInterfaceController {
    @Operation(summary = "Criar cliente", description = "Cria um cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cliente Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteCreateDTO clienteCreate);

    @Operation(summary = "Listar pagina de clientes", description = "Lista uma pagina de clientes")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Clientes Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<ClienteDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Listar pagina de clientes", description = "Lista uma pagina de clientes")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Clientes Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<PageDTO<ClienteDTO>> listarPorEmail(Integer pagina, Integer tamanho, @PathVariable("email") String email);

    @Operation(summary = "Listar pagina de clientes", description = "Lista uma pagina de clientes")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Clientes Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/nome/{nome}")
    public ResponseEntity<PageDTO<ClienteDTO>> listarPorNome(Integer pagina, Integer tamanho, @PathVariable("nome") String email);

    @Operation(summary = "Editar cliente", description = "Editar um cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cliente Editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCliente}")
    ResponseEntity<ClienteDTO> editar(@Valid @RequestBody ClienteCreateDTO clienteCreate, @PathVariable(name = "idCliente") Integer idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Deletar cliente", description = "Deleta o cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Cliente Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCliente}")
    ResponseEntity<Void> deletar(@PathVariable(name = "idCliente") Integer idUsuario) throws RegraDeNegocioException;
}
