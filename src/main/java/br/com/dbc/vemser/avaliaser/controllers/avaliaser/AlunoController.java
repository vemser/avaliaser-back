package br.com.dbc.vemser.avaliaser.controllers.avaliaser;

import br.com.dbc.vemser.avaliaser.controllers.adocumentation.OperationControllerAluno;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoFiltroDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/aluno")
public class AlunoController implements OperationControllerAluno {

    private final AlunoService alunoService;

    @GetMapping("/listar-alunos")
    public ResponseEntity<PageDTO<AlunoDTO>> listarAlunos(@RequestParam(required = false) Integer idAluno,
                                                          @RequestParam(required = false) String nome,
                                                          @RequestParam(required = false) String email,
                                                          Integer page, Integer size) throws RegraDeNegocioException {
        log.info("Buscando dados de Alunos...");
        PageDTO<AlunoDTO> aluno = alunoService.listarAlunoPaginado(idAluno, nome, email, page, size);
        log.info("Retorno de dados de Aluno em lista paginada, realizado com sucesso!");
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<PageDTO<AlunoDTO>> disponiveis(Integer page, Integer size) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.listarDisponiveis(page, size));
    }

    @GetMapping("/alunos-ativos-por-programa/{idPrograma}")
    public ResponseEntity<PageDTO<AlunoFiltroDTO>> listarAlunosAtivoPorProgramaTrilha(@RequestParam Integer page,
                                                                                      @RequestParam Integer size,
                                                                                      @PathVariable Integer idPrograma,
                                                                                      @RequestParam List<Integer> idTrilhas) throws RegraDeNegocioException {
        log.info("Buscando dados de Alunos por ID...");
        PageDTO<AlunoFiltroDTO> aluno = alunoService.listarAlunosAtivoPorProgramaTrilha(idPrograma,idTrilhas,page,size);
        log.info("Retorno de dados de Aluno por ID realizado com sucesso!");
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
    public ResponseEntity<AlunoDTO> cadastrarAluno(@Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        log.info("Salvando dados de cadastro do aluno...");
        AlunoDTO alunoDTO = alunoService.cadastrarAluno(alunoCreateDTO);
        log.info("Dados de cadastro salvos com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }


    @PutMapping(value = "/atualizar-aluno/{idAluno}")
    public ResponseEntity<AlunoDTO> atualizarAlunoPorId(@PathVariable("idAluno") Integer idAluno,
                                                        @Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        log.info("Salvando alterações de dados do aluno...");
        AlunoDTO aluno = alunoService.atualizarAlunoPorId(idAluno, alunoCreateDTO);
        log.info("Alteração de dados salva com sucesso!");
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletar/{idAluno}")
    public ResponseEntity<Void> desativarAluno(@PathVariable("idAluno") Integer idAluno) throws RegraDeNegocioException {
        log.info("Deletando aluno...");
        alunoService.desativarAlunoById(idAluno);
        log.info("Delete realizado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
