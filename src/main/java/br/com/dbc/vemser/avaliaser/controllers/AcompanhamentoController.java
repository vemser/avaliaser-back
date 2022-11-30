package br.com.dbc.vemser.avaliaser.controllers;

import br.com.dbc.vemser.avaliaser.controllers.documentation.OperationControllerAcompanhamento;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.AcompanhamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/acompanhamento")
public class AcompanhamentoController implements OperationControllerAcompanhamento {
    private final AcompanhamentoService acompanhamentoService;

    @GetMapping("/listar-acompanhamento")
    public ResponseEntity<PageDTO<AcompanhamentoDTO>> listarAcompanhamentos(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina) {
        PageDTO<AcompanhamentoDTO> acompanhamentos = acompanhamentoService.listarAcompanhamentosPaginados(paginaQueEuQuero, tamanhoDeRegistrosPorPagina);
        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
    }

    @GetMapping("/buscar-acompanhamento/{idAcompanhamento}")
    public ResponseEntity<AcompanhamentoDTO> buscarAcompanhamentosPorId(@RequestParam("idAcompanhamento") Integer idAcompanhamento) throws RegraDeNegocioException {
        AcompanhamentoDTO acompanhamento = acompanhamentoService.findByIdDTO(idAcompanhamento);
        return new ResponseEntity<>(acompanhamento, HttpStatus.OK);
    }

    @PutMapping(value = "/cadastrar-acompanhamento")
    public ResponseEntity<AcompanhamentoDTO> cadastrarAcompanhamento(
            @Valid @RequestBody AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException {
        AcompanhamentoDTO usuarioLogadoDTO = acompanhamentoService.cadastrarAcompanhamento(acompanhamentoCreateDTO);
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/editar-acompanhamento/{idAcompanhamento}")
    public ResponseEntity<AcompanhamentoDTO> editarAcompanhamento(@RequestParam("idAcompanhamento") Integer idAcompanhamento,
                                                                  @Valid @RequestBody EditarAcompanhamentoDTO editarAcompanhamentoDTO) throws RegraDeNegocioException {
        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.editarAcompanhamento(editarAcompanhamentoDTO,idAcompanhamento);
        return new ResponseEntity<>(acompanhamentoDTO, HttpStatus.OK);
    }

}
