package br.com.dbc.vemser.avaliaser.controllers;

import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.AlunoService;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/teste")
public class TesteController {

    private final UsuarioService usuarioService;
    private final AlunoService alunoService;


    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.excluirUsuariosTeste(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/aluno/{idAluno}")
    public ResponseEntity<Void> deletarAluno(@PathVariable("idAluno") Integer idAluno) throws RegraDeNegocioException {
        alunoService.excluirAlunosTeste(idAluno);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
