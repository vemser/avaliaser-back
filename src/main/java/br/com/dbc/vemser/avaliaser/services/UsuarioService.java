package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;



    public Integer getIdLoggedUser() {
        return Integer.parseInt(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    public UsuarioEntity getLoggedUser() throws IOException {
        return findById(getIdLoggedUser());
    }
    public UsuarioEntity findByEmail(String email) throws IOException {
        return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new IOException("USUARIO_NAO_ENCONTRADO"));
    }

    public UsuarioEntity findById(Integer idLoggedUser) throws IOException {
        return usuarioRepository.findById(idLoggedUser)
                .orElseThrow(() -> new IOException("USUARIO_NAO_ENCONTRADO"));
    }


    public UsuarioLogadoDTO cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO, Cargo cargo)
            throws IOException {
        CargoEntity cargoBanco = cargoService.findById(cargo.getInteger());
        String senhaEncode = passwordEncoder.encode(usuarioCreateDTO.getSenha());

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(usuarioCreateDTO.getNome());
        usuarioEntity.setEmail(usuarioCreateDTO.getEmail());
        usuarioEntity.setSenha(senhaEncode);
        usuarioEntity.setCargo(cargoBanco);
        usuarioEntity.setAtivo(Ativo.S);
        usuarioRepository.save(usuarioEntity);

        UsuarioLogadoDTO usuarioLogadoDTO = objectMapper.convertValue(usuarioEntity, UsuarioLogadoDTO.class);

        return usuarioLogadoDTO;
    }

    public UsuarioLogadoDTO atualizarUsuarioLogado(MultipartFile imagem, UsuarioCreateDTO usuarioCreateDTO) throws  IOException {
        UsuarioEntity usuarioRecuperado = getLoggedUser();
        return atualizarUsuario(imagem, usuarioCreateDTO, usuarioRecuperado.getIdUsuario());
    }

    public UsuarioLogadoDTO atualizarUsuario(MultipartFile imagem, UsuarioCreateDTO usuarioCreateDTO, Integer id) throws IOException {
        UsuarioEntity usuarioEntity = findById(id);

        usuarioEntity.setNome(usuarioCreateDTO.getNome());
        usuarioEntity.setEmail(usuarioCreateDTO.getEmail());
        usuarioEntity.setImage(getImagemEmBytes(imagem));
        UsuarioLogadoDTO usuarioLogadoDTO =
                objectMapper.convertValue(usuarioRepository.save(usuarioEntity),UsuarioLogadoDTO.class);
        return usuarioLogadoDTO;
    }

    public UsuarioLogadoDTO uploadImagem(MultipartFile imagem, Integer id) throws IOException {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).get();
        usuarioEntity.setImage(getImagemEmBytes(imagem));
        UsuarioLogadoDTO usuarioLogadoDTO =
                objectMapper.convertValue(usuarioRepository.save(usuarioEntity),UsuarioLogadoDTO.class);
        return usuarioLogadoDTO;
    }

    private static byte[] getImagemEmBytes(MultipartFile imagem) throws IOException {
        byte[] imagemBytes = imagem.getBytes();
        byte[] imagemRecebida = ImageUtil.compressImage(imagemBytes);
        return imagemRecebida;
    }


    public String loginUsuario(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(userPassAuthToken);

        Object principal = authenticate.getPrincipal();
        UsuarioEntity usuario = (UsuarioEntity) principal;
        return tokenService.getToken(usuario);
    }


    public UsuarioLogadoDTO getUsuarioLogado() throws IOException {

        UsuarioEntity usuario = getLoggedUser();
        UsuarioLogadoDTO usuarioLogado = new UsuarioLogadoDTO();
        usuarioLogado.setIdUsuario(usuario.getIdUsuario());
        usuarioLogado.setNome(usuario.getNome());
        usuarioLogado.setFoto(ImageUtil.decompressImage(usuario.getImage()));
        usuarioLogado.setCargo(usuario.getCargo().getNome());
        return usuarioLogado;
    }

    public void desativarUsuario() throws IOException {
        UsuarioEntity usuario = getLoggedUser();
        usuario.setAtivo(Ativo.N);
        usuarioRepository.save(usuario);
    }

    public void desativarUsuarioById(Integer id) throws IOException {
        UsuarioEntity usuario = findById(id);
        usuario.setAtivo(Ativo.N);
        usuarioRepository.save(usuario);
    }


}
