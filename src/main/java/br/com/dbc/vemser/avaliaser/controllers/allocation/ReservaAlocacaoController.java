package br.com.dbc.vemser.avaliaser.controllers.allocation;

import br.com.allocation.controller.interfaces.ReservaAlocacaoInterface;
import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ReservaAlocacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<PageDTO<ReservaAlocacaoDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(reservaAlocacaoService.listar(pagina, tamanho));
    }

    @Override
    public ResponseEntity<PageDTO<ReservaAlocacaoDTO>> filtrar(Integer pagina, Integer tamanho, String nomeAluno, String nomeVaga) {
        return ResponseEntity.ok(reservaAlocacaoService.filtrar(pagina, tamanho, nomeAluno, nomeVaga));
    }

    @Override
    public ResponseEntity<ReservaAlocacaoDTO> editar(@Valid @RequestBody ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO,
                                                     @PathVariable(name = "idReservaAlocacao") Integer idReservaAlocacao) throws RegraDeNegocioException {
        log.info("Editando Reserva alocação...");
        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.editar(idReservaAlocacao, reservaAlocacaoCreateDTO);
        log.info("Reserva alocação editado com sucesso!");
        return new ResponseEntity<>(reservaAlocacaoDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idReservaAlocacao) throws RegraDeNegocioException {
        reservaAlocacaoService.deletar(idReservaAlocacao);
        log.info("Reserva alocação deletada com sucesso");
        return ResponseEntity.noContent().build();
    }

}
