package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.avaliaser.OperationControllerFeedBack;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
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
    public ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackPaginado(Integer page, Integer size) throws RegraDeNegocioException {
        log.info("Realizando busca de feedbacks...");
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPaginados(page, size);
        log.info("Retorno de feedbacks em lista paginada realizado com sucesso!");
        return new ResponseEntity<>(feedBackDTOPageDTO, HttpStatus.OK);
    }

    @GetMapping("/listar-feedback-por-id/{idAluno}")
    public ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackPorAlunoPaginado(@PathVariable(required = false, name = "idAluno") Integer idAluno, Integer page, Integer size) throws RegraDeNegocioException {
        log.info("Realizando busca de feedbacks...");
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPorAlunoPaginados(idAluno, page, size);
        log.info("Retorno de feedbacks em lista paginada realizado com sucesso!");
        return new ResponseEntity<>(feedBackDTOPageDTO, HttpStatus.OK);
    }

    @GetMapping("/buscar-feedback/{idFeedBack}")
    public ResponseEntity<FeedBackDTO> buscarFeedBackPorId(@PathVariable("idFeedBack") Integer idFeedBack) throws RegraDeNegocioException {
        log.info("Realizando busca de feedbacks por ID...");
        FeedBackDTO feedBackDTO = feedbackService.findByIdDTO(idFeedBack);
        log.info("Retorno da busca de Feedback por id realizado com sucesso!");
        return new ResponseEntity<>(feedBackDTO, HttpStatus.OK);
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
}
