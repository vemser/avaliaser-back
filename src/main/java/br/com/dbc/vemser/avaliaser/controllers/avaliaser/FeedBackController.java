package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.OperationControllerFeedBack;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.avaliaser.FeedbackService;
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
@RequestMapping("/feedback")
public class FeedBackController implements OperationControllerFeedBack {

    private final FeedbackService feedbackService;

    @GetMapping("/listar-feedback")
    public ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackPaginado(@RequestParam(required = false) Integer idFeedback,
                                                                       @RequestParam(required = false) Integer idAluno,
                                                                       @RequestParam(required = false) String nome,
                                                                       Integer page, Integer size) throws RegraDeNegocioException {
        log.info("Realizando busca de feedbacks...");
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPaginados(idFeedback, idAluno, nome, page, size);
        log.info("Retorno de feedbacks em lista paginada realizado com sucesso!");
        return new ResponseEntity<>(feedBackDTOPageDTO, HttpStatus.OK);
    }

    @GetMapping("/listar-feedbacks-com-filtro")
    public ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackFiltrado(@RequestParam(required = false) Integer idAluno,
                                                                       @RequestParam(required = false) Integer idTrilha,
                                                                       @RequestParam(required = false) TipoAvaliacao situacao,
                                                                       @RequestParam(required = false) String nomeInstrutor,
                                                                       Integer page, Integer size) throws RegraDeNegocioException {

        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarPorFiltro(idAluno, idTrilha, situacao, nomeInstrutor, page, size);
        return new ResponseEntity<>(feedBackDTOPageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar-feedback")
    public ResponseEntity<FeedBackDTO> cadastrarFeedBack(@Valid @RequestBody FeedBackCreateDTO feedBackCreateDTO) throws RegraDeNegocioException {
        log.info("Salvando dados de cadastro de FeedBack...");
        FeedBackDTO feedBackDTO = feedbackService.cadastrarFeedBack(feedBackCreateDTO);
        log.info("Dados de cadastro de feedback salvos com sucesso!");
        return new ResponseEntity<>(feedBackDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/editar-feedback/{idFeedBack}")
    public ResponseEntity<FeedBackDTO> editarFeedBack(@PathVariable("idFeedBack") Integer idFeedBack,
                                                      @Valid @RequestBody EditarFeedBackDTO editarFeedBackDTO) throws RegraDeNegocioException {
        log.info("Salvando alterações dos dados de FeedBack...");
        FeedBackDTO feedBackDTO = feedbackService.editarFeedBack(idFeedBack, editarFeedBackDTO);
        log.info("Alteração de dados de FeedBack realizada com sucesso!");
        return new ResponseEntity<>(feedBackDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/desativar-feedback/{idFeedBack}")
    public ResponseEntity<FeedBackDTO> desativarFeed(@PathVariable("idFeedBack") Integer idFeedBack) throws RegraDeNegocioException {
        log.info("Desativando FeedBack...");
        feedbackService.desativarFeed(idFeedBack);
        log.info("FeedBack desativado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
