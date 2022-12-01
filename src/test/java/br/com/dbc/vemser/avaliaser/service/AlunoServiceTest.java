package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.AtualizarUsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.UsuarioRecuperacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
import br.com.dbc.vemser.avaliaser.factory.CargoFactory;
import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
import br.com.dbc.vemser.avaliaser.repositories.AlunoRepository;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.services.AlunoService;
import br.com.dbc.vemser.avaliaser.services.CargoService;
import br.com.dbc.vemser.avaliaser.services.EmailService;
import br.com.dbc.vemser.avaliaser.services.UsuarioService;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import freemarker.template.TemplateException;
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

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;
    @Mock
    private ImageUtil imageUtil;
    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private CargoService cargoService;

    @Mock
    private MultipartFile multipartFile;



    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(alunoService, "objectMapper", objectMapper);
    }


    @Test
    public void DeveListarUsuarioPaginadoCorretamente() {
        final int numeroPagina = 0;
        final int tamanho = 3;

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        PageImpl<AlunoEntity> listaPaginada = new PageImpl<>(List.of(aluno), PageRequest.of(numeroPagina, tamanho), 0);

        when(alunoRepository.findAllByAtivo(any(),any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarAlunoPaginado(numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(1, alunoDTOPageDTO.getTotalElementos());
        assertEquals(1, alunoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), alunoDTOPageDTO.getPagina());
    }

    @Test
    public void DeveListarUsuarioPaginadoComListaVazia() {
        final int numeroPagina = 0;
        final int tamanho = 0;
        List<AlunoDTO> listaVazia = new ArrayList<>();
        PageDTO<AlunoDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<AlunoDTO> paginaRecebida = alunoService.listarAlunoPaginado(numeroPagina, tamanho);
        assertEquals(paginaRecebida,alunoDTOPageDTOEsperado);

    }
    @Test
    public void deveTestarCadastroAlunoComSucesso() throws RegraDeNegocioException, IOException {

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());

        when(alunoRepository.save(any())).thenReturn(aluno);

        AlunoDTO alunoDTO = alunoService.cadastrarAluno(alunoCreateDTO,Stack.BACKEND);

        assertEquals(aluno.getNome(), alunoDTO.getNome());
        assertEquals(aluno.getIdAluno(), alunoDTO.getIdAluno());
        assertEquals(aluno.getStack(), alunoDTO.getStack());
        assertNotNull(alunoDTO);
    }



    @Test
    public void deveTestarUploadImagemComSucesso() throws RegraDeNegocioException {
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        byte[] imagemBytes = new byte[10*1024];
        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
        Integer idAluno = 1;

        when(alunoRepository.findByAtivoAndIdAluno(any(),anyInt())).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any())).thenReturn(aluno);

        AlunoDTO alunoDTO = alunoService.uploadImagem(imagem, idAluno);


        assertNotEquals(aluno.getFoto(), alunoDTO.getFoto());
        assertNotNull(alunoDTO);

    }

    @Test
    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        Integer idAluno = 1;

        when(alunoRepository.findByAtivoAndIdAluno(Ativo.S,aluno.getIdAluno())).thenReturn(Optional.of(aluno));
        AlunoDTO alunoDTO = alunoService.findByIdDTO(idAluno);

        assertNotNull(alunoDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer idAluno = 1;
        when(alunoRepository.findByAtivoAndIdAluno(any(),anyInt())).thenReturn(Optional.empty());
        alunoService.findById(idAluno);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestaCadastrarAlunoComErro() throws RegraDeNegocioException {
        AlunoCreateDTO alunoCreateDTO = null;
        alunoService.cadastrarAluno(alunoCreateDTO,Stack.BACKEND);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUploadImagemComErro() throws RegraDeNegocioException {
        byte[] imagemBytes = null;
        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
        Integer idAluno = 1;

        alunoService.uploadImagem(imagem, idAluno);

    }
    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException, IOException {
        Integer idAluno = 1;
        AlunoEntity alunoFactory = AlunoFactory.getAlunoEntity();
        when(alunoRepository.findByAtivoAndIdAluno(Ativo.S,idAluno)).thenReturn(Optional.of(alunoFactory));
        AlunoEntity alunoEntity = alunoService.findById(idAluno);
        assertEquals(alunoEntity,alunoFactory);
        assertNotNull(alunoEntity);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAtualizarAlunoPorIdComErro() throws RegraDeNegocioException {

        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
        Integer idAluno = 1;
        alunoService.atualizarAlunoPorId(idAluno,alunoCreateDTO,Stack.BACKEND);
    }

    @Test
    public void deveTestarAtualizarAlunoComSucesso() throws RegraDeNegocioException, IOException {

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();

        when(alunoRepository.findByAtivoAndIdAluno(any(),anyInt())).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any())).thenReturn(aluno);

        AlunoDTO alunoDTO = alunoService.atualizarAlunoPorId(aluno.getIdAluno(), alunoCreateDTO,aluno.getStack());

        assertNotNull(alunoDTO);
    }

    @Test
    public void deveTestarDesativarAlunoByIdComSucesso() throws RegraDeNegocioException, IOException {
        Integer id = 1;
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();

        when(alunoRepository.findByAtivoAndIdAluno(Ativo.S,aluno.getIdAluno())).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any())).thenReturn(aluno);

        alunoService.desativarAlunoById(id);

        verify(alunoRepository, times(1)).save(aluno);
    }

//    @Test(expected = RegraDeNegocioException.class)
//    public void deveLancarExcecaoAoExecutartransformarImagemEmBytesComIOException() throws RegraDeNegocioException, IOException {
//        byte[] imagemBytes = null;
//        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
//        doThrow(new IOException()).when(multipartFile).getBytes();
//        alunoService.transformarImagemEmBytes(imagem);
//    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }
}

