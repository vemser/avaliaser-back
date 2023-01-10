package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.ControllerAvaliacao;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
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

    @GetMapping
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listarAvaliacoesPaginados(@RequestParam Integer page,
                                                                           @RequestParam Integer size) throws RegraDeNegocioException {
        log.info("Listando Avaliações...");
        PageDTO<AvaliacaoDTO> lista = avaliacaoService.listarAvaliacoes(page, size);
        log.info("Listagem avaliações com sucesso.");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

//    @GetMapping("/{idAvaliacao}")
//    public ResponseEntity<PageDTO<AvaliacaoDTO>> listarPorAluno(@PathVariable("idAvaliacao") Integer idAvaliacao,
//                                                                @RequestParam Integer page,
//                                                                @RequestParam Integer size) throws RegraDeNegocioException {
//        log.info("Listando Avaliações por Aluno...");
//        PageDTO<AvaliacaoDTO> lista = avaliacaoService.listarAvaliacoesPorId(idAvaliacao, page, size);
//        log.info("Listagem avaliações por Aluno com sucesso.");
//        return new ResponseEntity<>(lista, HttpStatus.OK);
//    }

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

    @GetMapping("/listar-avaliacao-por-acompanhamento-aluno-ativo")
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listarAvaliacoes(@RequestParam(required = false)Integer idAcompanhamento,
                                                                                            @RequestParam(required = false)String tituloAcompanhamento,
                                                                                            @RequestParam(required = false)Integer idAluno,
                                                                                            @RequestParam(required = false) Ativo ativo,
                                                                                            Integer pagina,
                                                                                            Integer tamanho) throws RegraDeNegocioException {
        log.info("Realizando busca de dados em lista...");
        PageDTO<AvaliacaoDTO> avaliacoes = avaliacaoService.listarAvaliacaoPorAcompanhamentoAlunoSituacao(idAcompanhamento,
                tituloAcompanhamento,
                idAluno,
                ativo,
                pagina,
                tamanho);
        log.info("Retorno de dados em lista paginada realizado com sucesso!");
        return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
    }
}
