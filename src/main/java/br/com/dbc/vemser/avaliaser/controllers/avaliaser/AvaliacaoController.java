package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.ControllerAvaliacao;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/avaliacao")
public class AvaliacaoController implements ControllerAvaliacao {

    private final AvaliacaoService avaliacaoService;


    @PostMapping("/create")
    public ResponseEntity<AvaliacaoDTO> create(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando Avaliação de Acompanhamento...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.create(avaliacaoCreateDTO);
        log.info("Avaliação de Acompanhamento criado com sucesso.");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idAvaliacao}")
    public ResponseEntity<AvaliacaoDTO> update(@PathVariable("idAvaliacao") Integer idAvaliacao,
                                               @Valid @RequestBody EditarAvaliacaoDTO editarAvaliacaoDTO) throws RegraDeNegocioException {
        log.info("Editando Avaliação de Acompanhamento...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.update(idAvaliacao, editarAvaliacaoDTO);
        log.info("Editando de Avaliação de Acompanhamento com sucesso.");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/desativar/{idAvaliacao}")
    public ResponseEntity<Void> desativar(@PathVariable("idAvaliacao") Integer idAvaliacao) throws RegraDeNegocioException {
        log.info("Desativando avaliacao...");
        avaliacaoService.desativar(idAvaliacao);
        log.info("Desativação realizada com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/listar-avaliacao-por-acompanhamento-aluno")
    public ResponseEntity<PageDTO<AvaliacaoDTO>> filtrarAvaliacao(@RequestParam(required = false) Integer idAvaliacao,
                                                                  @RequestParam(required = false) TipoAvaliacao tipoAvaliacao,
                                                                  @RequestParam(required = false) String tituloAcompanhamento,
                                                                  @RequestParam(required = false) String nomeAluno,
                                                                  Integer pagina,
                                                                  Integer tamanho) throws RegraDeNegocioException {
        log.info("Realizando busca de dados em lista...");
        PageDTO<AvaliacaoDTO> avaliacoes = avaliacaoService.listarAvaliacaoPaginados(idAvaliacao,
                tipoAvaliacao,
                tituloAcompanhamento,
                nomeAluno,
                pagina,
                tamanho);
        log.info("Retorno de dados em lista paginada realizado com sucesso!");
        return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
    }
}
