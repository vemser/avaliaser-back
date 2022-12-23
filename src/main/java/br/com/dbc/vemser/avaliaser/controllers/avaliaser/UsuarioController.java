package br.com.dbc.vemser.avaliaser.controllers.avaliaser;


import br.com.dbc.vemser.avaliaser.controllers.adocumentation.avaliaser.OperationControllerAuth;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.AtualizarUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.TrocarSenhaUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.avaliaser.UsuarioService;
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
    public ResponseEntity<String> loginUsuario(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Logando usuário...");
        String token = usuarioService.loginUsuario(loginDTO);
        log.info("Usuário logado com sucesso.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping("/atualizar-usuario-logado")
    public ResponseEntity<UsuarioDTO> atualizarUsuarioLogado(@Valid @RequestBody AtualizarUsuarioLogadoDTO nome) throws RegraDeNegocioException {
        log.info("Buscando informações do usuario logado...");
        UsuarioDTO usuario = usuarioService.atualizarUsuarioLogado(nome);
        log.info("Dados de usuario logado retornados com sucesso!");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping(value = "/upload-imagem-usuario-logado/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioDTO> uploadImagemUsuarioLogado(@PathVariable(name = "idUsuario") Integer idUsuario,
                                                                @RequestPart(value = "file", required = false) MultipartFile file) throws RegraDeNegocioException {
        log.info("Salvando foto de perfil do usuario...");
        UsuarioDTO usuarioLogadoDTO = usuarioService.uploadImagem(file, idUsuario);
        log.info("Foto salva com sucesso!");
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<Void> recuperarSenha(@RequestParam String email) throws RegraDeNegocioException {
        log.info("Verificando dados e enviando email...");
        usuarioService.recuperarSenha(email);
        log.info("Email de recuperação enviado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/alterar-senha-usuario-logado")
    public ResponseEntity<Void> atualizarSenhaUsuarioLogado(@RequestBody @Valid TrocarSenhaUsuarioLogadoDTO senhas) throws RegraDeNegocioException {
        log.info("Verificando senha antiga, e salvando senha nova...");
        usuarioService.alterarSenhaUsuarioLogado(senhas);
        log.info("Senha alterada com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/alterar-senha-usuario-recuperacao")
    public ResponseEntity<Void> alterarSenhaRecuperada(@RequestParam String senha) throws RegraDeNegocioException {
        log.info("Verificando permissões e salvando senhas nova...");
        usuarioService.alterarSenhaPorRecuperacao(senha);
        log.info("Senha nova cadastrada com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
