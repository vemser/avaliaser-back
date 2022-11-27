package br.com.dbc.vemser.avaliaser.controllers;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> loginUsuario(@RequestBody LoginDTO loginDTO){
        log.info("Logando usuário...");
        String token = usuarioService.loginUsuario(loginDTO);
        log.info("Usuário logado com sucesso.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioLogadoDTO> getUsuarioLogado(){
        log.info("Retornando Usuário logado...");
        UsuarioLogadoDTO usuario = usuarioService.getUsuarioLogado();
        log.info("Retorno de usuário logado com sucesso.");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar-usuario", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioLogadoDTO> salvar(@RequestPart(value = "file") MultipartFile file,
                                                   @RequestParam String nome,
                                                   @RequestParam String email,
                                                   @RequestParam String senha,
                                                   @RequestParam Cargo cargo) throws IOException {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(email,senha,nome,cargo);
        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.cadastrarUsuario(usuarioCreateDTO, file);
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

}
