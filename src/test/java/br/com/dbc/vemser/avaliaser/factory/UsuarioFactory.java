//package br.com.dbc.vemser.avaliaser.factory;
//
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.login.UsuarioLogadoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.recuperacao.AtualizarUsuarioDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.recuperacao.UsuarioRecuperacaoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.AtualizarUsuarioLogadoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.UsuarioCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.UsuarioDTO;
//import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
//import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
//import br.com.dbc.vemser.avaliaser.enums.Ativo;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//public class UsuarioFactory {
//    public static UsuarioEntity getUsuarioEntity() {
//        UsuarioEntity usuario = new UsuarioEntity();
//        CargoEntity cargo = new CargoEntity();
//        cargo.setIdCargo(1);
//        cargo.setNome("ROLE_ADMIN");
//        usuario.setIdUsuario(1);
//        usuario.setNome("Paulo Sergio");
//        usuario.setEmail("paulo.sergio@dbccompany.com");
//        usuario.setSenha("123456789");
//        usuario.setCargo(cargo);
//        usuario.setAtivo(Ativo.S);
//        return usuario;
//    }
//
//    public static UsuarioCreateDTO getUsuarioCreateDTO() {
//        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
//        usuarioCreateDTO.setNome("Paulo Sergio");
//        usuarioCreateDTO.setEmail("paulo.sergio@dbccompany.com");
//        return usuarioCreateDTO;
//    }
//
//    public static AtualizarUsuarioDTO getAtualizarUsuario() {
//        AtualizarUsuarioDTO atualizarUsuarioDTO = new AtualizarUsuarioDTO();
//        atualizarUsuarioDTO.setNome("Paulo Sergio");
//        atualizarUsuarioDTO.setEmail("paulo.sergio@dbccompany.com");
//        return atualizarUsuarioDTO;
//    }
//
//    public static AtualizarUsuarioLogadoDTO getAtualizarUsuarioLogado() {
//        AtualizarUsuarioLogadoDTO atualizarUsuarioDTO = new AtualizarUsuarioLogadoDTO();
//        atualizarUsuarioDTO.setNome("Paulo Sergio");
//        return atualizarUsuarioDTO;
//    }
//
//    public static UsuarioLogadoDTO getUsuarioLogadoDTO() throws IOException {
//        byte[] imagemBytes = new byte[10 * 1024];
//        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
//        CargoEntity cargo = new CargoEntity();
//        cargo.setIdCargo(1);
//        UsuarioLogadoDTO usuarioLogadoDTO = new UsuarioLogadoDTO();
//        usuarioLogadoDTO.setNome("Paulo Sergio");
//        usuarioLogadoDTO.setEmail("paulo.sergio@dbccompany.com");
//        usuarioLogadoDTO.setCargo(cargo.getNome());
//        usuarioLogadoDTO.setFoto(imagem.getBytes());
//        return usuarioLogadoDTO;
//    }
//
//    public static UsuarioDTO getUsuarioDTO() throws IOException {
//        byte[] imagemBytes = new byte[10 * 1024];
//        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
//        CargoEntity cargo = new CargoEntity();
//        cargo.setIdCargo(1);
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setIdUsuario(1);
//        usuarioDTO.setNome("Paulo Sergio");
//        usuarioDTO.setCargo(usuarioDTO.getNome());
//        usuarioDTO.setFoto(imagem.getBytes());
//        usuarioDTO.setEmail("paulo.sergio@dbccompany.com");
//        return usuarioDTO;
//    }
//
//    public static UsuarioRecuperacaoDTO getUsuarioRecuperacao() {
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = new UsuarioRecuperacaoDTO();
//        usuarioRecuperacaoDTO.setEmail("paulo.sergio@dbccompany.com");
//        usuarioRecuperacaoDTO.setNome("Paulo Sergio");
//        usuarioRecuperacaoDTO.setSenha("123456789");
//        return usuarioRecuperacaoDTO;
//    }
//}
