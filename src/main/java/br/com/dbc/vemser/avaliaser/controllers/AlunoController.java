package br.com.dbc.vemser.avaliaser.controllers;

import br.com.dbc.vemser.avaliaser.controllers.documentation.OperationControllerAluno;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/aluno")
public class AlunoController implements OperationControllerAluno {

    private final AlunoService alunoService;

    @GetMapping("/listar-alunos")
    public ResponseEntity<PageDTO<AlunoDTO>> listarAlunos(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina) {
        log.info("Retornando Usuário logado...");
        PageDTO<AlunoDTO> aluno = alunoService.listarAlunoPaginado(paginaQueEuQuero, tamanhoDeRegistrosPorPagina);
        log.info("Retorno de usuário logado com sucesso.");
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }
    @GetMapping("/{idAluno}")
    public ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Integer idAluno) throws RegraDeNegocioException {
        AlunoDTO alunoDTO = alunoService.findByIdDTO(idAluno);
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar-aluno")
    public ResponseEntity<AlunoDTO> cadastrarAluno(@RequestParam Stack stack,
                                                       @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        AlunoDTO alunoDTO = alunoService.cadastrarAluno(alunoCreateDTO, stack);
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/upload-imagem/{idAluno}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AlunoDTO> uploadImagem(@RequestPart(value = "file", required = false) MultipartFile file,
                                                   @RequestParam(value = "idAluno") Integer idAluno) throws RegraDeNegocioException {
        AlunoDTO alunoDTO = alunoService.uploadImagem(file, idAluno);
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/atualizar-aluno/{idAluno}")
    public ResponseEntity<AlunoDTO> atualizarAlunoPorId(@RequestBody AlunoCreateDTO alunoCreateDTO,
                                                        @RequestParam Stack stack,
                                                            @PathVariable Integer idAluno) throws RegraDeNegocioException {
        alunoCreateDTO.setStack(stack);
        return new ResponseEntity<>(alunoService.atualizarUsuarioPorId(alunoCreateDTO, idAluno), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idAluno}")
    public ResponseEntity<Void> desativarAluno(@PathVariable Integer idAluno) throws RegraDeNegocioException {
        alunoService.desativarAlunoById(idAluno);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
