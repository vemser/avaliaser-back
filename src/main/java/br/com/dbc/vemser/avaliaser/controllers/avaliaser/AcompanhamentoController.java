//package br.com.dbc.vemser.avaliaser.controllers.avaliaser;
//
//import br.com.dbc.vemser.avaliaser.controllers.adocumentation.OperationControllerAcompanhamento;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.EditarAcompanhamentoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.AcompanhamentoService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@Slf4j
//@Validated
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/acompanhamento")
//public class AcompanhamentoController implements OperationControllerAcompanhamento {
//    private final AcompanhamentoService acompanhamentoService;
//
//    @GetMapping("/listar-acompanhamento")
//    public ResponseEntity<PageDTO<AcompanhamentoDTO>> listarAcompanhamentos(Integer page, Integer size) throws RegraDeNegocioException {
//        log.info("Realizando busca de dados em lista...");
//        PageDTO<AcompanhamentoDTO> acompanhamentos = acompanhamentoService.listarAcompanhamentosPaginados(page, size);
//        log.info("Retorno de dados em lista paginada realizado com sucesso!");
//        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
//    }
//
//    @GetMapping("/buscar-acompanhamento/{idAcompanhamento}")
//    public ResponseEntity<AcompanhamentoDTO> buscarAcompanhamentosPorId(@PathVariable("idAcompanhamento") Integer idAcompanhamento) throws RegraDeNegocioException {
//        log.info("Realizando busca de dados por id...");
//        AcompanhamentoDTO acompanhamento = acompanhamentoService.findByIdDTO(idAcompanhamento);
//        log.info("Retorno de dados do usuario por id realizado com sucesso!");
//        return new ResponseEntity<>(acompanhamento, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/cadastrar-acompanhamento")
//    public ResponseEntity<AcompanhamentoDTO> cadastrarAcompanhamento(
//            @Valid @RequestBody AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException {
//        log.info("Salvando cadastro do acompanhamento...");
//        AcompanhamentoDTO usuarioLogadoDTO = acompanhamentoService.cadastrarAcompanhamento(acompanhamentoCreateDTO);
//        log.info("Acompanhamento cadastrado com sucesso!");
//        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/editar-acompanhamento/{idAcompanhamento}")
//    public ResponseEntity<AcompanhamentoDTO> editarAcompanhamento(@PathVariable("idAcompanhamento") Integer idAcompanhamento,
//                                                                  @Valid @RequestBody EditarAcompanhamentoDTO editarAcompanhamentoDTO) throws RegraDeNegocioException {
//        log.info("Salvando alteração de dados do acompanhamento...");
//        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.editarAcompanhamento(editarAcompanhamentoDTO, idAcompanhamento);
//        log.info("Alterações de acompanhamento salvas com sucesso!");
//        return new ResponseEntity<>(acompanhamentoDTO, HttpStatus.OK);
//    }
//
//}
