package br.com.dbc.vemser.avaliaser.controllers;


import br.com.dbc.vemser.avaliaser.controllers.documentation.OperationControllerAdministrador;
import br.com.dbc.vemser.avaliaser.controllers.documentation.OperationControllerAuth;
import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.AtualizarUsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
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

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/administrador")
public class AdministradorController implements OperationControllerAdministrador {

    private final UsuarioService usuarioService;


    @GetMapping("/listar-usuarios")
    public ResponseEntity<PageDTO<UsuarioDTO>> listarUsuario(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina) {
        log.info("Retornando Usuário logado...");
        PageDTO<UsuarioDTO> usuario = usuarioService.listUsuarioPaginado(paginaQueEuQuero, tamanhoDeRegistrosPorPagina);
        log.info("Retorno de usuário logado com sucesso.");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuario = usuarioService.findByIdDTO(idUsuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }


    @PostMapping(value = "/cadastrar-usuario")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestParam Cargo cargo,
                                                       @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioDTO usuarioLogadoDTO = usuarioService.cadastrarUsuario(usuarioCreateDTO, cargo);
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/upload-imagem/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioDTO> uploadImagem(@RequestPart(value = "file", required = false) MultipartFile file,
                                                   @RequestParam(value = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuarioLogadoDTO = usuarioService.uploadImagem(file, idUsuario);
        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
    }


    @PutMapping(value = "/atualizar-usuario/{idUsuario}")
    public ResponseEntity<UsuarioDTO> atualizarUsuarioPorId(@RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO,
                                                            @PathVariable Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.atualizarUsuarioPorId(atualizarUsuarioDTO, idUsuario), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.desativarUsuarioById(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
