package br.com.dbc.vemser.avaliaser.controllers.allocation;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.ProgramaInterfaceController;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/programa")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProgramaController implements ProgramaInterfaceController {

    private final ProgramaService programaService;

    @Override
    public ResponseEntity<ProgramaDTO> salvar(@Valid @RequestBody ProgramaCreateDTO programaCreate) {

        log.info("Adicionando o Usuário...");
        ProgramaDTO programa = programaService.create(programaCreate);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer page, Integer size) throws RegraDeNegocioException {
        return ResponseEntity.ok(programaService.listar(page, size));
    }

    @Override
    public ResponseEntity<PageDTO<ProgramaDTO>> listarPorNome(Integer page, Integer size, String nome) throws RegraDeNegocioException {
        return ResponseEntity.ok(programaService.listarPorNome(page, size, nome));
    }

    @Override
    public ResponseEntity<PageDTO<ProgramaDTO>> listarPorId(Integer idPrograma) throws RegraDeNegocioException {
        return ResponseEntity.ok(programaService.listarPorId(idPrograma));
    }

    @Override
    public ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaCreateDTO programaCreate, Integer idPrograma) throws RegraDeNegocioException {
        log.info("Editando o Programa...");
        ProgramaDTO programa = programaService.editar(idPrograma, programaCreate);
        log.info("Programa editado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idPrograma) throws RegraDeNegocioException {
        programaService.deletar(idPrograma);
        log.info("Programa deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
