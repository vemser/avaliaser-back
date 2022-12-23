package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
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
@RequestMapping("/avaliacao-acompanhamento")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listarAvaliacoesPaginados(@RequestParam Integer page,
                                                                           @RequestParam Integer size) throws RegraDeNegocioException {
        log.info("Listando Avaliações...");
        PageDTO<AvaliacaoDTO> lista = avaliacaoService.listarAvaliacoesPaginados(page, size);
        log.info("Listagem avaliações com sucesso.");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{idAluno}")
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listarPorAlunoPaginado(@PathVariable("idAluno") Integer idAluno,
                                                                        @RequestParam Integer page,
                                                                        @RequestParam Integer size) throws RegraDeNegocioException {
        log.info("Listando Avaliações por Aluno...");
        PageDTO<AvaliacaoDTO> lista = avaliacaoService.listarAvaliacoesPorAlunoPaginados(idAluno, page, size);
        log.info("Listagem avaliações por Aluno com sucesso.");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/cadastrar-avaliacao")
    public ResponseEntity<AvaliacaoDTO> cadastrarAvaliacao(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        log.info("Cadastranndo Avaliação de Acompanhamento...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.cadastrarAvaliacao(avaliacaoCreateDTO);
        log.info("Cadastro de Avaliação de Acompanhamento com sucesso.");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idAvaliacao}")
    public ResponseEntity<AvaliacaoDTO> editandoAvaliacao(@PathVariable("idAvaliacao") Integer idAvaliacao,
                                                          @Valid @RequestBody EditarAvaliacaoDTO editarAvaliacaoDTO) throws RegraDeNegocioException {
        log.info("Editando Avaliação de Acompanhamento...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.editarAvaliacao(idAvaliacao, editarAvaliacaoDTO);
        log.info("Editando de Avaliação de Acompanhamento com sucesso.");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.OK);
    }
}