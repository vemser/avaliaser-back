package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.CargoFactory;
import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.services.CargoService;
import br.com.dbc.vemser.avaliaser.services.EmailService;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CargoService cargoService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @Mock
    private EmailService emailService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarcadastroComSucesso(){
        CargoEntity cargo = CargoFactory.getCargo();
        String senha = "123";
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        UsuarioCreateDTO usuarioCreateDTO = UsuarioFactory.getUsuarioCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());

        when(cargoService.findById(anyInt())).thenReturn(cargo);
        when(passwordEncoder.encode(anyString())).thenReturn(senha);
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioRetorno = usuarioService.cadastrarUsuario(usuarioCreateDTO, Cargo.ADMIN);

        assertEquals(usuario.getNome(), usuarioRetorno.getNome());
        assertNotNull(usuarioRetorno);
    }

    @Test
    public void deveTestarLoginComSucesso(){
        String senha = "123";
        String email = "test@test.com.br";
        String token = "token";

        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setEmail(senha);

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, senha);

        when(tokenService.getToken(any())).thenReturn(token);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        String tokenRetorno = usuarioService.loginUsuario(loginDTO);

        assertEquals(token, tokenRetorno);
    }

    @Test
    public void deveTestarUploadImagemComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        byte[] imagemBytes = new byte[10*1024];
        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
        Integer idUsuario = 1;

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.uploadImagem(imagem, idUsuario);


        assertNotEquals(usuario.getImage(), usuarioLogadoDTO.getFoto());
        assertNotNull(usuarioLogadoDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarGetUsuarioLogadoComErro() throws RegraDeNegocioException {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        usuarioService.getUsuarioLogado();

    }

    @Test
    public void deveTestarGetUsuarioLogadoComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        byte[] imagemBytes = new byte[10*1024];
        usuario.setImage(imagemBytes);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        usuarioService.getUsuarioLogado();

    }

    @Test
    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        Integer idUsuario = 1;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        UsuarioDTO usuarioRetorno = usuarioService.findByIdDTO(idUsuario);

        assertNotNull(usuarioRetorno);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer idUsuario = 1;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        usuarioService.findById(idUsuario);

    }
    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException, IOException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        Integer idUsuario = 1;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        UsuarioEntity usuarioRetorno = usuarioService.findById(idUsuario);

        assertNotNull(usuarioRetorno);

    }

    @Test
    public void deveTestarFindAllComSucesso(){
        List<UsuarioDTO> lista = new ArrayList<>();
        UsuarioDTO usuarioDTO= new UsuarioDTO();
        lista.add(usuarioDTO);
        List<UsuarioEntity> usuarioEntityList = new ArrayList<>();
        usuarioEntityList.add(UsuarioFactory.getUsuarioEntity());
        when(usuarioRepository.findAll()).thenReturn(usuarioEntityList);

        List<UsuarioDTO> listaRetorno = usuarioService.findAll();

        assertEquals(1,listaRetorno.size());
    }
    @Test
    public void deveTestarAtualizarUsuarioComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        UsuarioCreateDTO usuarioCreateDTO = UsuarioFactory.getUsuarioCreateDTO();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.atualizarUsuario(usuarioCreateDTO, 1);

        assertNotNull(usuarioLogadoDTO);
    }

    @Test
    public void deveTestarDesativarUsuarioByIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        usuarioService.desativarUsuarioById(id);

        verify(usuarioRepository, times(1)).save(usuario);
    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }
}
