package br.com.dbc.vemser.avaliaser.controllers.vemrankser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.vemrankser.ModuloService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/modulo")
public class ModuloController {

    private final ModuloService moduloService;

    @Operation(summary = "Editar Modulo", description = "Editar modulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modulo editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/editar")
    public ResponseEntity<ModuloDTO> editar(@RequestBody @Valid ModuloCreateDTO modulo,Integer id) throws RegraDeNegocioException {
        log.info("Editando modulo....");
        ModuloDTO moduloDTO = moduloService.editar(id,modulo);
        log.info("Modulo editado com sucesso....");
        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
    }
    @Operation(summary = "Desativar Modulo", description = "Desativar modulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modulo desativado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/desativar")
    public ResponseEntity<Void> desativar(Integer idUsuario) throws RegraDeNegocioException {
        moduloService.desativar(idUsuario);
        log.info("Modulo desativado com sucesso");
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Adicionar novo Modulo", description = "Adicionar novos modulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modulo adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adicionar")
    public ResponseEntity<ModuloDTO> adicionar(@RequestBody @Valid ModuloCreateDTO modulo) throws RegraDeNegocioException {
        log.info("Criando modulo....");
        ModuloDTO moduloDTO = moduloService.criar(modulo);
        log.info("Modulo Criado com sucesso....");
        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
    }
    @Operation(summary = "Clonar modulo", description = "Clonar modulo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modulo clonado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/clonar/{idModulo}")
    public ResponseEntity<ModuloDTO> clonar(@PathVariable(name = "idModulo") Integer idModulo) throws RegraDeNegocioException {
        log.info("Clonando modulo....");
        ModuloDTO moduloDTO = moduloService.clonarModulo(idModulo);
        log.info("Modulo clonado com sucesso...");
        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
    }
//    @Operation(summary = "Vincular modulos trilha", description = "Vincular modulo a trilha do banco de dados")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Foi vinculado  com sucesso"),
//                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PostMapping("/vincular-modulo-trilha/{idModulo}/{idTrilha}")
//    public ResponseEntity<ModuloDTO> vincularModuloTrilha(@PathVariable(name = "idModulo") Integer idModulo,
//                                                          @PathVariable(name = "idTrilha") Integer idTrilha) throws RegraDeNegocioException {
//        log.info("Vinculando modulo....");
//        ModuloDTO moduloDTO = moduloService.vincularModuloTrilha(idModulo, idTrilha);
//        log.info("Vinculado com sucesso....");
//        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
//    }

    @Operation(summary = "Pega modulo pelo id", description = "Resgata o modulo pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-id-modulo")
    public ResponseEntity<ModuloDTO> findById(Integer idModulo) throws RegraDeNegocioException {
        return new ResponseEntity<>(moduloService.findById(idModulo), HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista dos modulos", description = "Resgata a lista de modulos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-todos-modulos")
    public ResponseEntity<PageDTO<ModuloDTO>> findAllModulos(Integer page, Integer size) {
        return new ResponseEntity<>(moduloService.listarModulo(page,size), HttpStatus.OK);
    }

}
