//package br.com.dbc.vemser.avaliaser.controllers.vemrankser;
//
//import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
//import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
//import br.com.vemrankser.ranqueamento.dto.ModuloTrilhaDTO;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.service.ModuloService;
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
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@Validated
//@RequestMapping("/modulo")
//public class ModuloController {
//
//    private final ModuloService moduloService;
//
//    @Operation(summary = "Adicionar novo Modulo", description = "Adicionar novos modulos")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Modulo adicionado com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PostMapping("/adicionar-modulo")
//    public ResponseEntity<ModuloDTO> adicionar(@RequestBody @Valid ModuloCreateDTO modulo) {
//        log.info("Criando modulo....");
//        ModuloDTO moduloDTO = moduloService.adicionar(modulo);
//        log.info("Modulo Criado com sucesso....");
//        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
//    }
//
//    @PostMapping("/vincular-modulo-trilha/{idModulo}/{idTrilha}")
//    public ResponseEntity<ModuloDTO> vincular(@PathVariable(name = "idModulo") Integer idModulo, @PathVariable(name = "idTrilha") Integer idTrilha, @RequestBody ModuloTrilhaDTO moduloTrilhaDTO) throws RegraDeNegocioException {
//        log.info("Criando modulo....");
//        ModuloDTO moduloDTO = moduloService.vincularModuloTrilha(idModulo, idTrilha, moduloTrilhaDTO);
//        log.info("Modulo Criado com sucesso....");
//        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
//    }
//
//    @Operation(summary = "Pega modulo pelo id", description = "Resgata o modulo pelo id do banco de dados")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
//                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/find-id-modulo")
//    public ResponseEntity<ModuloDTO> findById(Integer idModulo) throws RegraDeNegocioException {
//        return new ResponseEntity<>(moduloService.findById(idModulo), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Pega a lista dos modulos", description = "Resgata a lista de modulos do banco de dados")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
//                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/lista-todos-modulos")
//    public ResponseEntity<List<ModuloDTO>> findAllModulos() {
//        return new ResponseEntity<>(moduloService.listAllModulos(), HttpStatus.OK);
//    }
//
//}
