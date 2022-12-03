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

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/aluno")
public class AlunoController implements OperationControllerAluno {

    private final AlunoService alunoService;

    @GetMapping("/listar-alunos")
    public ResponseEntity<PageDTO<AlunoDTO>> listarAlunos(Integer page, Integer size) throws RegraDeNegocioException {
        log.info("Buscando dados de Alunos...");
        PageDTO<AlunoDTO> aluno = alunoService.listarAlunoPaginado(page, size);
        log.info("Retorno de dados de Aluno em lista paginada, realizado com sucesso!");
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }

    @GetMapping("/{idAluno}")
    public ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Integer idAluno) throws RegraDeNegocioException {
        log.info("Buscando dados de Alunos por ID...");
        AlunoDTO alunoDTO = alunoService.findByIdDTO(idAluno);
        log.info("Retorno de dados de Aluno por ID realizado com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar-aluno")
    public ResponseEntity<AlunoDTO> cadastrarAluno(@RequestParam Stack stack,
                                                   @Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        log.info("Salvando dados de cadastro do aluno...");
        AlunoDTO alunoDTO = alunoService.cadastrarAluno(alunoCreateDTO, stack);
        log.info("Dados de cadastro salvos com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/upload-imagem/{idAluno}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AlunoDTO> uploadImagem(@PathVariable("idAluno") Integer idAluno,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws RegraDeNegocioException {
        log.info("Salvando foto de cadastro do aluno...");
        AlunoDTO alunoDTO = alunoService.uploadImagem(file, idAluno);
        log.info("Imagem de cadastro salva com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/atualizar-aluno/{idAluno}")
    public ResponseEntity<AlunoDTO> atualizarAlunoPorId(@PathVariable("idAluno") Integer idAluno,
                                                        @RequestParam Stack stack,
                                                        @Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        log.info("Salvando alterações de dados do aluno...");
        AlunoDTO aluno = alunoService.atualizarAlunoPorId(idAluno, alunoCreateDTO, stack);
        log.info("Alteração de dados salva com sucesso!");
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idAluno}")
    public ResponseEntity<Void> desativarAluno(@PathVariable Integer idAluno) throws RegraDeNegocioException {
        log.info("Realizando desativação do aluno...");
        alunoService.desativarAlunoById(idAluno);
        log.info("Desativação do aluno realizada com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
