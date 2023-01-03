package br.com.dbc.vemser.avaliaser.controllers.allocation;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.TecnologiaInterfaceController;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.allocation.TecnologiaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/tecnologia")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TecnologiaController implements TecnologiaInterfaceController {
    private final TecnologiaService tecnologiaService;

    @GetMapping("/tecnologia-busca")
    public ResponseEntity<PageDTO<TecnologiaDTO>> buscar(@RequestParam(required = false) String nomeTecnologia,
                                                         Integer page, Integer size) throws RegraDeNegocioException {
        log.info("Buscando Tecnologias...");
        PageDTO<TecnologiaDTO> tecnologia = tecnologiaService.buscarPorTecnologia(nomeTecnologia, page, size);
        log.info("Retorno de tecnologias em lista paginada, realizado com sucesso!");
        return new ResponseEntity<>(tecnologia, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TecnologiaDTO> create(@RequestBody @Valid TecnologiaCreateDTO tecnologia) {
        log.info("Criando Tecnologia...");
        TecnologiaDTO tecnologiaDTO = tecnologiaService.create(tecnologia);
        log.info("Tecnologia Criada!!");
        return new ResponseEntity<>(tecnologiaDTO, HttpStatus.OK);
    }

    @GetMapping("/{idTecnologia}")
    public ResponseEntity<TecnologiaDTO> BuscarTecnologiaPorId(@PathVariable("idTecnologia") Integer idTecnologia) throws RegraDeNegocioException {
        log.info("Buscando Tecnologia...");
        TecnologiaDTO tecnologiaDTO = tecnologiaService.findByIdDTO(idTecnologia);
        log.info("Tecnologia encontrada!");
        return new ResponseEntity<>(tecnologiaDTO, HttpStatus.OK);
    }
}

