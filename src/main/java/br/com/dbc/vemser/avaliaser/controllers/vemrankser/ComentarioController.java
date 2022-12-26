//package br.com.dbc.vemser.avaliaser.controllers.vemrankser;
//
//import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoCreateDTO;
//import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoDTO;
//import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
//import br.com.vemrankser.ranqueamento.dto.PageDTO;
//import br.com.vemrankser.ranqueamento.enums.TipoFeedback;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.service.ComentarioService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@Validated
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/comentario")
//public class ComentarioController {
//
//    private final ComentarioService comentarioService;
//
//    @Operation(summary = "Comentario de atividade", description = "Cadastrar comentario para atividade")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Cadastro de comentario com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PutMapping("/avaliar-comentar-atividade")
//    public ResponseEntity<AtividadeComentarioAvaliacaoDTO> adicionarComentarioAvaliar(@RequestBody @Valid AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, Integer idAluno, Integer idAtividade) throws RegraDeNegocioException {
//
//        log.info("Criando novo comentario....");
//        AtividadeComentarioAvaliacaoDTO comentarioAvaliacaoDTO = comentarioService.adicionarComentarioAvaliar(atividadeComentarioAvaliacaoCreateDTO, idAluno, idAtividade);
//        log.info("Comentario criado com sucesso!");
//
//        return new ResponseEntity<>(comentarioAvaliacaoDTO, HttpStatus.OK);
//    }
//
//
//    @Operation(summary = "Listar comentários por id atividade", description = "Listar comentários por atividade")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-comentario")
//    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorAtividade(Integer idAtividade) throws RegraDeNegocioException {
//        return ResponseEntity.ok(comentarioService.listarComentarioPorAtividade(idAtividade));
//    }
//
//
//    @Operation(summary = "Listar comentários por feedback positivo e negativo", description = "Listar comentários")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-por-feedback")
//    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorFeedback(TipoFeedback tipoFeedback) {
//        return ResponseEntity.ok(comentarioService.listarComentarioPorFeedback(tipoFeedback));
//    }
//
//    @Operation(summary = "Listar comentários do aluno", description = "Listar comentários de um aluno")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-comentarios-aluno")
//    public ResponseEntity<PageDTO<ComentarioDTO>> listarComentariosAluno(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, Integer idAluno) {
//        return ResponseEntity.ok(comentarioService.comentariosDoAluno(pagina, tamanho, idAluno));
//    }
//
//    @Operation(summary = "Adicionar feedback ao aluno", description = "Listar comentários de um aluno")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PostMapping("/adicionar-feedback")
//    public ResponseEntity<ComentarioDTO> adicionarFeedback(@RequestBody ComentarioDTO comentario, Integer idAluno, TipoFeedback tipoFeedback) throws RegraDeNegocioException {
//        return ResponseEntity.ok(comentarioService.adicionarFeedback(comentario, idAluno, tipoFeedback));
//    }
//}
