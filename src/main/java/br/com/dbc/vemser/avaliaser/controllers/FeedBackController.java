package br.com.dbc.vemser.avaliaser.controllers;

import br.com.dbc.vemser.avaliaser.controllers.documentation.OperationControllerFeedBack;
import br.com.dbc.vemser.avaliaser.dto.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.FeedbackService;
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
        public ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackPaginado(Integer page, Integer size) {
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPaginados(page, size);
        return new ResponseEntity<>(feedBackDTOPageDTO, HttpStatus.OK);
    }
    @GetMapping("/listar-feedback-por-id/{idAluno}")
    public ResponseEntity<PageDTO<FeedBackDTO>> listarFeedBackPorAlunoPaginado(@PathVariable(required = false, name = "idAluno")Integer idAluno, Integer page, Integer size) {
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPorAlunoPaginados(idAluno,page, size);
        return new ResponseEntity<>(feedBackDTOPageDTO, HttpStatus.OK);
    }

    @GetMapping("/buscar-feedback/{idFeedBack}")
    public ResponseEntity<FeedBackDTO> buscarFeedBackPorId(@PathVariable("idFeedBack") Integer idFeedBack) throws RegraDeNegocioException {
        FeedBackDTO feedBackDTO = feedbackService.findByIdDTO(idFeedBack);
        return new ResponseEntity<>(feedBackDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar-feedback")
    public ResponseEntity<FeedBackDTO> cadastrarFeedBack(@Valid @RequestBody FeedBackCreateDTO feedBackCreateDTO) throws RegraDeNegocioException {
        FeedBackDTO feedBackDTO = feedbackService.cadastrarFeedBack(feedBackCreateDTO);
        return new ResponseEntity<>(feedBackDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/editar-feedback/{idFeedBack}")
    public ResponseEntity<FeedBackDTO> editarFeedBack(@PathVariable("idFeedBack") Integer idFeedBack,
                                                                  @Valid @RequestBody EditarFeedBackDTO editarFeedBackDTO) throws RegraDeNegocioException {
        FeedBackDTO feedBackDTO = feedbackService.editarFeedBack(idFeedBack,editarFeedBackDTO);
        return new ResponseEntity<>(feedBackDTO, HttpStatus.OK);
    }
}
