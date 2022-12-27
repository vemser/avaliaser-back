package br.com.dbc.vemser.avaliaser.controllers.vemrankser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.OperationControllerComentario;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadecomentariodto.AtividadeComentarioAvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadecomentariodto.AtividadeComentarioAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.comentariodto.ComentarioDTO;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.vemrankser.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comentario")
public class ComentarioController implements OperationControllerComentario {

    private final ComentarioService comentarioService;

    @Override
    @PutMapping("/avaliar-comentar-atividade")
    public ResponseEntity<AtividadeComentarioAvaliacaoDTO> adicionarComentarioAvaliar(@RequestBody @Valid AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, Integer idAluno, Integer idAtividade) throws RegraDeNegocioException {

        log.info("Criando novo comentario....");
        AtividadeComentarioAvaliacaoDTO comentarioAvaliacaoDTO = comentarioService.adicionarComentarioAvaliar(atividadeComentarioAvaliacaoCreateDTO, idAluno, idAtividade);
        log.info("Comentario criado com sucesso!");

        return new ResponseEntity<>(comentarioAvaliacaoDTO, HttpStatus.OK);
    }


    @Override
    @GetMapping("/listar-comentario")
    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return ResponseEntity.ok(comentarioService.listarComentarioPorAtividade(idAtividade));
    }


    @Override
    @GetMapping("/listar-por-feedback")
    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorFeedback(Situacao situacao) {
        return ResponseEntity.ok(comentarioService.listarComentarioPorFeedback(situacao));
    }

    @Override
    @GetMapping("/listar-comentarios-aluno")
    public ResponseEntity<PageDTO<ComentarioDTO>> listarComentariosAluno(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size, Integer idAluno) throws RegraDeNegocioException {
        return ResponseEntity.ok(comentarioService.comentariosDoAluno(page, size, idAluno));
    }

    @Override
    @PostMapping("/adicionar-feedback")
    public ResponseEntity<ComentarioDTO> adicionarFeedback(@RequestBody ComentarioDTO comentario, Integer idAluno, Situacao situacao) throws RegraDeNegocioException {
        return ResponseEntity.ok(comentarioService.adicionarFeedback(comentario, idAluno, situacao));
    }

    @PutMapping("/desativar")
    public ResponseEntity<Void> desativarComentario(@PathVariable Integer idComentario) throws RegraDeNegocioException {
        log.info("Realizando desativação do comentário...");
        comentarioService.desativarComentario(idComentario);
        log.info("Desativação do comentário realizada com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
