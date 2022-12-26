//package br.com.dbc.vemser.avaliaser.controllers.vemrankser;
//
//import br.com.vemrankser.ranqueamento.dto.LinkDTO;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.service.LinkService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@Validated
//@RequiredArgsConstructor
//@RequestMapping("/link")
//public class LinkController {
//
//    private final LinkService linkService;
//
//
//    @Operation(summary = "Enviar link", description = "Enviar link para o instrutor no banco de dados")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Link enviado com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PostMapping("/enviar")
//    public ResponseEntity<LinkDTO> enviar(@Valid @RequestBody LinkDTO link, Integer idAluno, Integer idAtividade) throws RegraDeNegocioException {
//        return new ResponseEntity<>(linkService.create(idAtividade, idAluno, link), HttpStatus.CREATED);
//    }
//}
