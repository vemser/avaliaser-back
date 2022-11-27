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
    public ResponseEntity<String> loginUsuario(@RequestBody LoginDTO loginDTO) {
        log.info("Logando usuário...");
        String token = usuarioService.loginUsuario(loginDTO);
        log.info("Usuário logado com sucesso.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioLogadoDTO> getUsuarioLogado() throws IOException {
        log.info("Retornando Usuário logado...");
        UsuarioLogadoDTO usuario = usuarioService.getUsuarioLogado();
        log.info("Retorno de usuário logado com sucesso.");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar-usuario")
    public ResponseEntity<UsuarioLogadoDTO> cadastrarUsuario(@RequestParam Cargo cargo,
                                                             @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws IOException{
    UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.cadastrarUsuario(usuarioCreateDTO, cargo);
        return new ResponseEntity<>(usuarioLogadoDTO,HttpStatus.OK);
}
    @PutMapping(value = "/upload-imagem/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioLogadoDTO> uploadImagem(@RequestPart(value = "file") MultipartFile file,
                                                         @RequestParam(value = "idUsuario") Integer idUsuario) throws IOException {
            UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.uploadImagem(file, idUsuario);
            return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

    @PutMapping(value ="/atualizar-usuario-logado", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioLogadoDTO> updateUsuario(@RequestPart(value = "file") MultipartFile file,
                                                          @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws IOException {
        return new ResponseEntity<>(usuarioService.atualizarUsuarioLogado(file, usuarioCreateDTO), HttpStatus.OK);
    }

    @PutMapping(value ="/atualizar-usuario-byId", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioLogadoDTO> updateUsuarioById(@RequestPart(value = "file") MultipartFile file,
                                                              @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO,
                                                              @PathVariable Integer idUsuario ) throws IOException {
        return new ResponseEntity<>(usuarioService.atualizarUsuario(file, usuarioCreateDTO, idUsuario), HttpStatus.OK);
    }


    @DeleteMapping("/delete-usuario-logado")
    public void delete() throws IOException {
        usuarioService.desativarUsuario();
    }

    @DeleteMapping("/delete-usuarioByID/{idUsuario}")
    public ResponseEntity<Void> deletarUsuarioCliente(@PathVariable Integer idUsuario) throws IOException {
        usuarioService.desativarUsuarioById(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
