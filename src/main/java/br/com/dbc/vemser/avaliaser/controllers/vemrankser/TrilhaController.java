package br.com.dbc.vemser.avaliaser.controllers.vemrankser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.TrilhaControllerInterface;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.rankdto.RankingDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/trilha")
public class TrilhaController implements TrilhaControllerInterface {

    private final TrilhaService trilhaService;


    @PostMapping
    public ResponseEntity<TrilhaDTO> create(@RequestBody
                                                @Valid TrilhaCreateDTO trilhaCreateDTO){
        log.info("Criando Trilha...");
        TrilhaDTO trilhaDTO = trilhaService.create(trilhaCreateDTO);
        log.info("Trilha Criada com sucesso!!");
        return new ResponseEntity<>(trilhaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{idTrilha}")
    public ResponseEntity<TrilhaDTO> update(@PathVariable(name = "idTrilha") Integer idTrilha,
                                            @RequestBody TrilhaCreateDTO trilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.updateTrilha(idTrilha, trilha), HttpStatus.OK);

    }

    @GetMapping("/lista-ranking")
    public ResponseEntity<PageDTO<RankingDTO>> listRanking(Integer page,
                                                        Integer size,
                                                        Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.rankingTrilha(page,size,idTrilha), HttpStatus.OK);
    }

    @GetMapping("/lista-trilha-nome")
    public ResponseEntity<PageDTO<TrilhaDTO>> findTrilhaByNome(@RequestParam(required = false) String nome,
                                                            @RequestParam int page,
                                                            @RequestParam int size) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.findTrilhaByNome(nome, PageRequest.of(page, size)), HttpStatus.OK);
    }


    @GetMapping("/find-id-trilha")
    public ResponseEntity<TrilhaDTO> findById(Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.getIdTrilha(idTrilha), HttpStatus.OK);
    }


    @GetMapping("/lista-trilha-page")
    public ResponseEntity<PageDTO<TrilhaDTO>> listAllTrilhaPaginado(@RequestParam Integer page,
                                                                    @RequestParam Integer size) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.listarAllTrilhaPaginado(page, size), HttpStatus.OK);
    }

    @DeleteMapping("/{idTrilha}")
    public ResponseEntity<Void> desativar(@PathVariable(name = "idTrilha") Integer idTrilha) throws RegraDeNegocioException {
        log.info("Desativando Trilha");
        trilhaService.desativar(idTrilha);
        log.info("Trilha Desativada com sucesso!");
        return ResponseEntity.noContent().build();
    }


}