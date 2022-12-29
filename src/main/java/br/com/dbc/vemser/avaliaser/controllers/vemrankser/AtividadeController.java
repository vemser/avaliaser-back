package br.com.dbc.vemser.avaliaser.controllers.vemrankser;

import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto.AtividadeCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto.AtividadeDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadepagedto.AtividadePaginacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.vemrankser.AtividadeService;
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
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/atividade")
public class AtividadeController {

    private final AtividadeService atividadeService;


    @Operation(summary = "Cadastro de atividade", description = "Cadastrar atividade para os módulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de atividade com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AtividadeDTO> create(@RequestBody @Valid AtividadeCreateDTO atividadeCreateDTO) throws RegraDeNegocioException {

        log.info("Criando nova atividade....");
        AtividadeDTO atividadeDTO = atividadeService.createAtividade(atividadeCreateDTO);
        log.info("Atividade criada com sucesso!");

        return new ResponseEntity<>(atividadeDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar Atividade", description = "Atualizar Ativade no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha atualizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/update/{idAtividade}")
    public ResponseEntity<AtividadeDTO> updateAtividade(@PathVariable(name = "idAtividade") Integer idAtividade, @RequestBody AtividadeCreateDTO atividade) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.atualizarAtividade(idAtividade, atividade), HttpStatus.OK);

    }


    @Operation(summary = "Listar atividade com paginação", description = "Listar atividade com paginação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar de atividade com paginação, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-paginado")
    public ResponseEntity<AtividadePaginacaoDTO<AtividadeDTO>> listarAtividadePaginado(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size) throws RegraDeNegocioException {
        return ResponseEntity.ok(atividadeService.listarAtividades(page, size));
    }

//    @Operation(summary = "Colocar a atividade como concluida", description = "Concluir atividade da turma")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Atividade concluida com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PutMapping("/concluir-atividade/{idAtividade}")
//    public ResponseEntity<AtividadeDTO> avaliarAtividade(@PathVariable(name = "idAtividade") Integer idAtividade) throws RegraDeNegocioException {
//        return new ResponseEntity<>(atividadeService.colocarAtividadeComoConcluida(idAtividade), HttpStatus.OK);
//
//    }

//    @Operation(summary = "Listar atividade por status", description = "Listar atividade por status")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Listar atividade por status com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-trilha-status")
//    public ResponseEntity<PageDTO<AtividadeTrilhaDTO>> listarAtividadePorStatus(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "10") Integer tamanho, @RequestParam(required = false) Integer idTrilha, @RequestParam(required = false) AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
//        return new ResponseEntity<>(atividadeService.listarAtividadePorStatus(pagina, tamanho, idTrilha, atividadeStatus), HttpStatus.OK);
//    }

//    @Operation(summary = "Listar atividade no mural instrutor", description = "Listar atividade no mural instrutor")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Listar atividade no mural do instrutor com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-mural-instrutor")
//    public ResponseEntity<PageDTO<AtividadeMuralDTO>> listarAtividadeMural(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(required = false) Integer idTrilha) throws RegraDeNegocioException {
//        return new ResponseEntity<>(atividadeService.listarAtividadeMuralInstrutor(pagina, tamanho, idTrilha), HttpStatus.OK);
//    }

//    @Operation(summary = "Listar atividade no mural do aluno", description = "Listar atividade no mural do aluno")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Listar atividade no mural do aluno com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-mural-aluno")
//    public ResponseEntity<PageDTO<AtividadeMuralAlunoDTO>> listarAtividadeMuralAluno(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(required = false) AtividadeStatus atividadeStatus, @RequestParam(required = false) Integer idUsuario) throws RegraDeNegocioException {
//        return new ResponseEntity<>(atividadeService.listarAtividadeMuralAluno(pagina, tamanho, idUsuario, atividadeStatus), HttpStatus.OK);
//    }

//    @Operation(summary = "Listar atividade por trilha e modulo", description = "Listar atividade por trilha e modulo")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "201", description = "Listar atividade por trilha e modulo com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/listar-trilha-modulo")
//    public ResponseEntity<PageDTO<AtividadeNotaPageDTO>> listarAtividadePorNota(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(required = false, defaultValue = "2") Integer idTrilha, @RequestParam(required = false, defaultValue = "1") Integer idModulo, @RequestParam(required = false) AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
//        return new ResponseEntity<>(atividadeService.listarAtividadePorIdTrilhaIdModulo(pagina, tamanho, idTrilha, idModulo, atividadeStatus), HttpStatus.OK);
//    }


    @Operation(summary = "Pega a atividade pelo id", description = "Resgata a atividade pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-id-atividade")
    public ResponseEntity<AtividadeDTO> findById(Integer idAtividade) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.findById(idAtividade), HttpStatus.OK);
    }


    @Operation(summary = "Desativar atividade", description = "Desativa atividade pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Foi desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar-atividade")
    public ResponseEntity<Void> desativar(Integer idAtividade) throws RegraDeNegocioException {
        atividadeService.desativar(idAtividade);
        return ResponseEntity.noContent().build();
    }

}
