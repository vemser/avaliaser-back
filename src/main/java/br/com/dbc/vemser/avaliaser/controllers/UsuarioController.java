package br.com.dbc.vemser.avaliaser.controllers;


import br.com.dbc.vemser.avaliaser.controllers.documentation.OperationControllerAuth;
import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.AtualizarUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.TrocarSenhaUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
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
@RequestMapping("/auth")
public class UsuarioController implements OperationControllerAuth {

    private final UsuarioService usuarioService;


    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioLogadoDTO> getUsuarioLogado() throws RegraDeNegocioException {
        log.info("Retornando Usuário logado...");
        UsuarioLogadoDTO usuario = usuarioService.getUsuarioLogado();
        log.info("Retorno de usuário logado com sucesso.");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody LoginDTO loginDTO) {
        log.info("Logando usuário...");
        String token = usuarioService.loginUsuario(loginDTO);
        log.info("Usuário logado com sucesso.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping("/atualizar-usuario-logado")
    public ResponseEntity<UsuarioDTO> atualizarUsuarioLogado(@RequestBody @Valid AtualizarUsuarioLogadoDTO nome) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.atualizarUsuarioLogado(nome), HttpStatus.OK);

    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<Void> recuperarSenha(@RequestParam String email) throws RegraDeNegocioException {
        usuarioService.recuperarSenha(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/alterar-senha-usuario-logado")
    public ResponseEntity<Void> atualizarSenhaUsuarioLogado(@RequestBody @Valid TrocarSenhaUsuarioLogadoDTO senhas) throws RegraDeNegocioException {
        usuarioService.alterarSenhaUsuarioLogado(senhas);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping( "/alterar-senha-usuario-recuperacao")
    public ResponseEntity<Void> alterarSenhaRecuperada(@RequestParam String senha) throws RegraDeNegocioException {
        usuarioService.alterarSenhaPorRecuperacao(senha);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
