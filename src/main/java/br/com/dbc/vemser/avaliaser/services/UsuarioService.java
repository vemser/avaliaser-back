package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.Deflater;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;



    public UsuarioLogadoDTO cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO, MultipartFile imagem) throws IOException {
        CargoEntity cargo = cargoService.findById(usuarioCreateDTO.getCargo().getInteger());
        String senhaEncode = passwordEncoder.encode(usuarioCreateDTO.getSenha());


        byte[] imagemBytes = imagem.getBytes();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(usuarioCreateDTO.getNome());
        usuarioEntity.setEmail(usuarioCreateDTO.getEmail());
        usuarioEntity.setSenha(senhaEncode);
        usuarioEntity.setCargo(cargo);
        usuarioEntity.setImage(ImageUtil.compressImage(imagemBytes));
        usuarioEntity.setAtivo(Ativo.S);
        usuarioRepository.save(usuarioEntity);

        UsuarioLogadoDTO usuarioLogadoDTO = objectMapper.convertValue(usuarioEntity, UsuarioLogadoDTO.class);

        return usuarioLogadoDTO;
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

    public UsuarioLogadoDTO getUsuarioLogado(){
        String idUsuario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Integer idUsuarioLogado = Integer.valueOf(idUsuario);
        UsuarioEntity usuario = usuarioRepository.findById(idUsuarioLogado).get();
        UsuarioLogadoDTO usuarioLogado = new UsuarioLogadoDTO();
        usuarioLogado.setNome(usuario.getNome());
        usuarioLogado.setFoto(ImageUtil.decompressImage(usuario.getImage()));
        usuarioLogado.setCargo(usuario.getCargo().getNome());
        return usuarioLogado;
    }

}
