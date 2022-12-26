//package br.com.dbc.vemser.avaliaser.controllers.avaliaser;
//
//
//import br.com.dbc.vemser.avaliaser.controllers.adocumentation.OperationControllerAdministrador;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.recuperacao.AtualizarUsuarioDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.UsuarioCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.UsuarioDTO;
//import br.com.dbc.vemser.avaliaser.enums.Cargo;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.UsuarioService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//
//@Slf4j
//@Validated
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/administrador")
//public class AdministradorController implements OperationControllerAdministrador {
//
//    private final UsuarioService usuarioService;
//
//    @GetMapping("/listar-usuarios")
//    public ResponseEntity<PageDTO<UsuarioDTO>> listarUsuario(@RequestParam Integer page, @RequestParam Integer size) throws RegraDeNegocioException {
//        log.info("Retornando Usuário logado...");
//        PageDTO<UsuarioDTO> usuario = usuarioService.listUsuarioPaginado(page, size);
//        log.info("Retorno de usuário logado com sucesso.");
//        return new ResponseEntity<>(usuario, HttpStatus.OK);
//    }
//
//    @GetMapping("/usuario/{idUsuario}")
//    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
//        log.info("Retornando busca de usuario por id...");
//        UsuarioDTO usuario = usuarioService.findByIdDTO(idUsuario);
//        log.info("Busca realizada com sucesso!");
//        return new ResponseEntity<>(usuario, HttpStatus.OK);
//
//    }
//
//    @PostMapping(value = "/cadastrar-usuario")
//    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestParam Cargo cargo,
//                                                       @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
//        log.info("Salvando dados de cadastro...");
//        UsuarioDTO usuarioLogadoDTO = usuarioService.cadastrarUsuario(usuarioCreateDTO, cargo);
//        log.info("Cadastro realizado com sucesso!");
//        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/upload-imagem/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<UsuarioDTO> uploadImagem(@PathVariable(name = "idUsuario") Integer idUsuario,
//                                                   @RequestPart(value = "file", required = false) MultipartFile file) throws RegraDeNegocioException {
//        log.info("Salvando foto de perfil do usuario...");
//        UsuarioDTO usuarioLogadoDTO = usuarioService.uploadImagem(file, idUsuario);
//        log.info("Foto salva com sucesso!");
//        return new ResponseEntity<>(usuarioLogadoDTO, HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/atualizar-usuario/{idUsuario}")
//    public ResponseEntity<UsuarioDTO> atualizarUsuarioPorId(@PathVariable("idUsuario") Integer idUsuario,
//                                                            @RequestBody @Valid AtualizarUsuarioDTO atualizarUsuarioDTO) throws RegraDeNegocioException {
//        log.info("Salvando alteração de dados do usuario...");
//        UsuarioDTO usuario = usuarioService.atualizarUsuarioPorId(atualizarUsuarioDTO, idUsuario);
//        log.info("Alteração realizada com sucesso!");
//        return new ResponseEntity<>(usuario, HttpStatus.OK);
//
//    }
//
//    @DeleteMapping("/delete/{idUsuario}")
//    public ResponseEntity<Void> deletarUsuario(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
//        log.info("Desativando usuario...");
//        usuarioService.desativarUsuarioById(idUsuario);
//        log.info("Usuario desativado com sucesso!");
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//}
