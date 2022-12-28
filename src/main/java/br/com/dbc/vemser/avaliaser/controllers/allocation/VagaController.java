package br.com.dbc.vemser.avaliaser.controllers.allocation;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.VagaInterfaceController;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.allocation.VagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaga")
@Validated
@RequiredArgsConstructor
@Slf4j
public class VagaController implements VagaInterfaceController {

    private final VagaService vagaService;

    @Override
    public ResponseEntity<VagaDTO> salvar(VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando a vaga...");
        VagaDTO vaga = vagaService.salvar(vagaCreateDTO);
        log.info("Vaga adicionado com sucesso!");
        return new ResponseEntity<>(vaga, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<VagaDTO>> listar(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return ResponseEntity.ok(vagaService.listar(pagina, tamanho));
    }

    @Override
    public ResponseEntity<PageDTO<VagaDTO>> listarPoId(Integer idVaga) throws RegraDeNegocioException {
        return ResponseEntity.ok(vagaService.listarPorId(idVaga));
    }

    @Override
    public ResponseEntity<PageDTO<VagaDTO>> listarPorNome(Integer pagina, Integer tamanho, String nome) throws RegraDeNegocioException {
        return ResponseEntity.ok(vagaService.listarPorNome(pagina, tamanho, nome));
    }

    @Override
    public ResponseEntity<VagaDTO> editar(Integer idVaga, VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException {

        log.info("Editando a vaga...");
        VagaDTO vaga = vagaService.editar(idVaga, vagaCreateDTO);
        log.info("Vaga editada com sucesso!");
        return new ResponseEntity<>(vaga, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idVaga) throws RegraDeNegocioException {
        vagaService.deletar(idVaga);
        log.info("Vaga deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
