package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.AtualizarUsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.TrocarSenhaUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.CargoFactory;
import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.services.CargoService;
import br.com.dbc.vemser.avaliaser.services.EmailService;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void deveTestarcadastroComSucesso() throws RegraDeNegocioException {
        CargoEntity cargo = CargoFactory.getCargo();
        String senha = "123";
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        UsuarioCreateDTO usuarioCreateDTO = UsuarioFactory.getUsuarioCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());

        when(cargoService.findById(anyInt())).thenReturn(cargo);
        when(passwordEncoder.encode(anyString())).thenReturn(senha);
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioRetorno = usuarioService.cadastrarUsuario(usuarioCreateDTO, Cargo.ADMIN);

        assertEquals(usuario.getIdUsuario(), usuarioRetorno.getIdUsuario());
        assertEquals(usuario.getNome(), usuarioRetorno.getNome());
        assertEquals(usuario.getEmail(), usuarioRetorno.getEmail());
        assertEquals(usuario.getCargo().getNome(), usuarioRetorno.getCargo());
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

        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.uploadImagem(imagem, idUsuario);


        assertNotEquals(usuario.getImage(), usuarioDTO.getFoto());
        assertNotNull(usuarioDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUploadImagemComErro() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        byte[] imagemBytes = null;
        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
        Integer idUsuario = 1;

        usuarioService.uploadImagem(imagem, idUsuario);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarGetUsuarioLogadoComErro() throws RegraDeNegocioException {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(usuarioRepository.findByAtivoAndIdUsuario(any(),anyInt())).thenReturn(Optional.empty());
        usuarioService.getUsuarioLogado();

    }

    @Test
    public void deveTestarGetUsuarioLogadoComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        byte[] imagemBytes = new byte[10*1024];
        usuario.setImage(imagemBytes);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        usuarioService.getUsuarioLogado();

    }

    @Test
    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        Integer idUsuario = 1;
        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        UsuarioDTO usuarioRetorno = usuarioService.findByIdDTO(idUsuario);

        assertNotNull(usuarioRetorno);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer idUsuario = 1;
        when(usuarioRepository.findByAtivoAndIdUsuario(any(),anyInt())).thenReturn(Optional.empty());
        usuarioService.findById(idUsuario);

    }
    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException, IOException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        Integer idUsuario = 1;
        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        UsuarioEntity usuarioRetorno = usuarioService.findById(idUsuario);

        assertNotNull(usuarioRetorno);

    }

    @Test
    public void DeveListarUsuarioPaginadoCorretamente() {
        final int numeroPagina = 0;
        final int tamanho = 3;

        UsuarioEntity usuario = getUsuarioEntity();
        PageImpl<UsuarioEntity> listaPaginada = new PageImpl<>(List.of(usuario), PageRequest.of(numeroPagina, tamanho), 0);

        when(usuarioRepository.findAllByAtivo(any(),any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<UsuarioDTO> usuarioDTOPageDTO = usuarioService.listUsuarioPaginado(numeroPagina, tamanho);

        assertNotNull(usuarioDTOPageDTO);
        assertEquals(1, usuarioDTOPageDTO.getTotalElementos());
        assertEquals(1, usuarioDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), usuarioDTOPageDTO.getPagina());
    }


    @Test
    public void deveAlterarSenhaCorretamente() throws RegraDeNegocioException {
        final String senhaNova = "1234admin";

        UsuarioEntity usuario = getUsuarioEntity();

        UsuarioEntity usuarioEsperado = getUsuarioEntity();
        usuarioEsperado.setSenha(senhaNova);

        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEsperado);
        when(passwordEncoder.encode(senhaNova)).thenReturn(senhaNova);
        UsuarioEntity usuarioAtualizado = usuarioService.alterarSenha(senhaNova, usuario.getIdUsuario());

        assertEquals(senhaNova, usuarioAtualizado.getSenha());
    }

    @Test
    public void deveAlterarSenhaEmRecuperacaoCorretamente() throws RegraDeNegocioException {
        final String senhaNova = "1234admin";

        UsuarioEntity usuario = getUsuarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        UsuarioEntity usuarioEsperado = getUsuarioEntity();
        usuarioEsperado.setSenha(senhaNova);

        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEsperado);
        when(passwordEncoder.encode(senhaNova)).thenReturn(senhaNova);

        usuarioService.alterarSenhaPorRecuperacao(senhaNova);

        assertEquals(senhaNova, usuarioEsperado.getSenha());
    }

    @Test
    public void deveMudarSenhaCorretamenteUsuarioLogado() throws RegraDeNegocioException {

        final String senhaAntiga = "admin";
        final String senhaNova = "1234admin";
        TrocarSenhaUsuarioLogadoDTO senhas = new TrocarSenhaUsuarioLogadoDTO();
        senhas.setSenhaAntiga(senhaAntiga);
        senhas.setSenhaNova(senhaNova);
        UsuarioEntity usuario = getUsuarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        usuario.setSenha(senhaAntiga);
        UsuarioEntity usuarioEsperado = getUsuarioEntity();
        usuarioEsperado.setSenha(senhaNova);

        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEsperado);
        when(passwordEncoder.matches(senhaAntiga, usuario.getSenha())).thenReturn(true);
        when(passwordEncoder.encode(senhaNova)).thenReturn(senhaNova);
        usuarioService.alterarSenhaUsuarioLogado(senhas);
    }

    @Test
    public void deveRecuperaSenhaCorretamente() throws RegraDeNegocioException {
        final String email = "test@demo.com.br";
        UsuarioEntity usuario = getUsuarioEntity();
        when(usuarioRepository.findByEmailAndAtivo(anyString(),any())).thenReturn(Optional.of(usuario));
        usuarioService.recuperarSenha(email);
        verify(tokenService).retornarTokenRecuperacaoSenha(any());
        verify(emailService).sendEmailRecuperacao(any(), any(),any());
    }

    @Test
    public void deveTestarAtualizarUsuarioComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        AtualizarUsuarioDTO atualizarUsuarioDTO = new AtualizarUsuarioDTO();
        when(usuarioRepository.findByAtivoAndIdUsuario(any(),anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.atualizarUsuarioPorId(atualizarUsuarioDTO,1);

        assertNotNull(usuarioDTO);
    }

    @Test
    public void deveTestarDesativarUsuarioByIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();

        when(usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        usuarioService.desativarUsuarioById(id);

        verify(usuarioRepository, times(1)).save(usuario);
    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }

    private static UsuarioEntity getUsuarioEntity() {
        final String email = "test@demo.com.br";
        final String nome = "Teste Nome";
        final String senha = "123";
        final CargoEntity cargo = getCargo();

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setCargo(cargo);
        usuario.setAtivo(Ativo.S);
        usuario.setSenha(senha);

        return usuario;
    }

    private static CargoEntity getCargo() {

        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(2);
        cargo.setNome(Cargo.GESTOR.name());

        return cargo;
    }
}

