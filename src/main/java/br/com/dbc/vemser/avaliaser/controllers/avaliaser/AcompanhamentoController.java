package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.OperationControllerAcompanhamento;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AcompanhamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/acompanhamento")
public class AcompanhamentoController implements OperationControllerAcompanhamento {
    private final AcompanhamentoService acompanhamentoService;

    @GetMapping("/listar-acompanhamento")
    public ResponseEntity<PageDTO<AcompanhamentoDTO>> listarAcompanhamentos(@RequestParam(required = false)Integer idAcompanhamento,
                                                                            @RequestParam(required = false)String nomePrograma,
                                                                            @RequestParam(required = false)String tituloAcompanhamento, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        log.info("Realizando busca de dados em lista...");
        PageDTO<AcompanhamentoDTO> acompanhamentos = acompanhamentoService.listarAcompanhamentosPaginados(idAcompanhamento,nomePrograma,tituloAcompanhamento,pagina, tamanho);
        log.info("Retorno de dados em lista paginada realizado com sucesso!");
        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
    }

    @PostMapping(value = "/criar")
    public ResponseEntity<AcompanhamentoDTO> create(
            @Valid @RequestBody AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException {
        log.info("Salvando cadastro do acompanhamento...");
        AcompanhamentoDTO usuarioLogadoDTO = acompanhamentoService.create(acompanhamentoCreateDTO);
        log.info("Acompanhamento cadastrado com sucesso!");
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/editar/{idAcompanhamento}")
    public ResponseEntity<AcompanhamentoDTO> update(@PathVariable("idAcompanhamento") Integer idAcompanhamento,
                                                    @Valid @RequestBody AcompanhamentoCreateDTO createDTO) throws RegraDeNegocioException {
        log.info("Salvando alteração de dados do acompanhamento...");
        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.update(createDTO, idAcompanhamento);
        log.info("Alterações de acompanhamento salvas com sucesso!");
        return new ResponseEntity<>(acompanhamentoDTO, HttpStatus.OK);
    }
    @DeleteMapping(value = "/desativar/{idAcompanhamento}")
    public ResponseEntity<Void> desativar(@PathVariable("idAcompanhamento") Integer idAcompanhamento) throws RegraDeNegocioException {
        log.info("Desativando acompanhamento...");
        acompanhamentoService.desativar(idAcompanhamento);
        log.info("Desativação realizada com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
