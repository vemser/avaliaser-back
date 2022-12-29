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
import org.springframework.data.domain.PageRequest;
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
    private final ObjectMapper objectMapper;

    @GetMapping("/tecnologia-busca")
    public PageDTO<TecnologiaDTO> buscar(@RequestParam String nomeTecnologia,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        return tecnologiaService.buscarPorTecnologia(nomeTecnologia, PageRequest.of(page, size));
    }

    @PostMapping
    public ResponseEntity<TecnologiaDTO> create(@RequestBody @Valid TecnologiaCreateDTO tecnologia) {
        log.info("Criando Tecnologia...");
        TecnologiaDTO tecnologiaDTO = tecnologiaService.create(tecnologia);
        log.info("tecnologia Criada!!");
        return new ResponseEntity<>(tecnologiaDTO, HttpStatus.OK);
    }

    @GetMapping("/{idTecnologia}")
    public ResponseEntity<TecnologiaDTO> findByIdTecnologia(@PathVariable("idTecnologia") Integer idTecnologia){
        log.info("Buscando Tecnologia...");
        TecnologiaDTO enderecoDTO = objectMapper.convertValue(tecnologiaService.findByIdTecnologia(idTecnologia), TecnologiaDTO.class);
        log.info("Tecnologia encontrada!");
        return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
    }
}

