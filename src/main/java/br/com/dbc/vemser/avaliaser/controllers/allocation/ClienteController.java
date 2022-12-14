package br.com.dbc.vemser.avaliaser.controllers.allocation;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.ClienteInterfaceController;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.allocation.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements ClienteInterfaceController {

    private final ClienteService clienteService;

    @Override
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteCreateDTO clienteCreate) throws RegraDeNegocioException {
        log.info("Adicionando o Cliente...");
        ClienteDTO cliente = clienteService.salvar(clienteCreate);
        log.info("Cliente adicionado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<ClienteDTO>> listar(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return ResponseEntity.ok(clienteService.listar(pagina, tamanho));
    }
    @GetMapping("/listar-clientes-inativos")
    public ResponseEntity<PageDTO<ClienteDTO>> listarInativos(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return ResponseEntity.ok(clienteService.listarInativos(pagina, tamanho));
    }

    @Override
    public ResponseEntity<PageDTO<ClienteDTO>> listarPorEmail(Integer pagina, Integer tamanho, String email) throws RegraDeNegocioException {
        return ResponseEntity.ok(clienteService.listarPorEmail(pagina, tamanho, email));
    }

    @Override
    public ResponseEntity<PageDTO<ClienteDTO>> listarPorNome(Integer pagina, Integer tamanho, String nome) throws RegraDeNegocioException {
        return ResponseEntity.ok(clienteService.listarPorNome(pagina, tamanho, nome));
    }

    @Override
    public ResponseEntity<ClienteDTO> editar(@Valid @RequestBody ClienteCreateDTO clienteCreate, Integer idCliente) throws RegraDeNegocioException {
        log.info("Editando o Cliente...");
        ClienteDTO cliente = clienteService.editar(idCliente, clienteCreate);
        log.info("Cliente editado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idUsuario) throws RegraDeNegocioException {
        clienteService.deletar(idUsuario);
        log.info("Cliente deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reativar-cliente")
    public ResponseEntity<ClienteDTO> reativarCliente(Integer idUsuario) throws RegraDeNegocioException {
        ClienteDTO clienteDTO = clienteService.reativarCliente(idUsuario);
        log.info("Cliente reativado com sucesso");
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }
}
