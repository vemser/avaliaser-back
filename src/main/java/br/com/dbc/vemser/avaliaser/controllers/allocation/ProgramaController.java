package br.com.dbc.vemser.avaliaser.controllers.allocation;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.ProgramaInterfaceController;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaEdicaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/programa")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProgramaController implements ProgramaInterfaceController {

    private final ProgramaService programaService;

    @Override
    public ResponseEntity<ProgramaDTO> create(@Valid @RequestBody ProgramaCreateDTO programaCreate) throws RegraDeNegocioException {

        log.info("Criando programa ...");
        ProgramaDTO programa = programaService.create(programaCreate);
        log.info("Programa adicionado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer page, Integer size) throws RegraDeNegocioException {
        return ResponseEntity.ok(programaService.listar(page, size));
    }

    @Override
    public ResponseEntity<PageDTO<ProgramaDTO>> listarPorNome(@RequestParam Integer page,
                                                              @RequestParam Integer size,
                                                              @RequestParam String nome) throws RegraDeNegocioException {
        return ResponseEntity.ok(programaService.listarPorNome(page, size, nome));
    }

    @Override
    public ResponseEntity<ProgramaDTO> pegarPrograma(Integer idPrograma) throws RegraDeNegocioException {
        return ResponseEntity.ok(programaService.buscarProgramaPorId(idPrograma));
    }

    @Override
    public ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaEdicaoDTO programaEdicaoDTO, Integer idPrograma) throws RegraDeNegocioException {
        log.info("Editando o Programa...");
        ProgramaDTO programa = programaService.editar(idPrograma, programaEdicaoDTO);
        log.info("Programa editado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> desativar(Integer idPrograma) throws RegraDeNegocioException {
        programaService.desativar(idPrograma);
        log.info("Programa desativado com sucesso");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/fechar-programa")
    public ResponseEntity<ProgramaDTO> fecharPrograma(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaDTO programa = programaService.fecharPrograma(idPrograma);
        log.info("Programa desativado com sucesso");
        return new ResponseEntity<>(programa, HttpStatus.OK);
    }
}
