package br.com.dbc.vemser.avaliaser.controllers;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping("/listar-usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuario() {
        log.info("Retornando Usuário logado...");
        List<UsuarioDTO> usuario = usuarioService.findAll();
        log.info("Retorno de usuário logado com sucesso.");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuario = usuarioService.findByIdDTO(idUsuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

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

    @PostMapping(value = "/cadastrar-usuario")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestParam Cargo cargo,
                                                       @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        UsuarioDTO usuarioLogadoDTO = usuarioService.cadastrarUsuario(usuarioCreateDTO, cargo);
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/upload-imagem/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioLogadoDTO> uploadImagem(@RequestPart(value = "file", required = false) MultipartFile file,
                                                         @RequestParam(value = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.uploadImagem(file, idUsuario);
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }


    @PutMapping(value = "/atualizar-usuario/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioLogadoDTO> updateUsuarioById(@RequestParam String nome,
                                                              @RequestParam String email,
                                                              @PathVariable Integer idUsuario) throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(email, nome);
        return new ResponseEntity<>(usuarioService.atualizarUsuario(usuarioCreateDTO, idUsuario), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.desativarUsuarioById(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
