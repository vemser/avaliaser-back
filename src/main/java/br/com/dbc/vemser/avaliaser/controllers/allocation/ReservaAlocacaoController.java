package br.com.dbc.vemser.avaliaser.controllers.allocation;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.ReservaAlocacaoInterface;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoEditarDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.allocation.ReservaAlocacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reserva-alocacao")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ReservaAlocacaoController implements ReservaAlocacaoInterface {
    private final ReservaAlocacaoService reservaAlocacaoService;

    @Override
    public ResponseEntity<ReservaAlocacaoDTO> salvar(@Valid @RequestBody ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando a Reserva alocação...");
        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.salvar(reservaAlocacaoCreateDTO);
        log.info("Reserva alocação adicionado com sucesso!");
        return new ResponseEntity<>(reservaAlocacaoDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<ReservaAlocacaoDTO>> filtrar(Integer pagina, Integer tamanho,
                                                               @RequestParam(name = "nome", required = false) String nome) throws RegraDeNegocioException {
        log.info("Listando Reservas e Alocações...");
        PageDTO<ReservaAlocacaoDTO> listagem = reservaAlocacaoService.filtrar(pagina, tamanho, nome);
        log.info("Listagem realizada com sucesso.");
        return ResponseEntity.ok(listagem);
    }

    @Override
    public ResponseEntity<ReservaAlocacaoDTO> editar(@Valid @RequestBody ReservaAlocacaoEditarDTO reservaAlocacaoEditarDTO,
                                                     @PathVariable(name = "idReservaAlocacao") Integer idReservaAlocacao) throws RegraDeNegocioException {
        log.info("Editando Reserva alocação...");
        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.editar(idReservaAlocacao, reservaAlocacaoEditarDTO);
        log.info("Reserva alocação editado com sucesso!");
        return new ResponseEntity<>(reservaAlocacaoDTO, HttpStatus.CREATED);
    }

}
