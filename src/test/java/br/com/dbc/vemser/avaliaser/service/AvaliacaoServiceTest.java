//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
//import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
//import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
//import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
//import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
//import br.com.dbc.vemser.avaliaser.enums.Cargo;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.factory.AcompanhamentoFactory;
//import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
//import br.com.dbc.vemser.avaliaser.factory.AvaliacaoFactory;
//import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
//import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AvaliacaoRepository;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.AcompanhamentoService;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.AvaliacaoService;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.UsuarioService;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AvaliacaoServiceTest {
//
//    @InjectMocks
//    private AvaliacaoService avaliacaoService;
//
//    @Mock
//    private AvaliacaoRepository avaliacaoRepository;
//    @Mock
//    private AcompanhamentoService acompanhamentoService;
//    @Mock
//    private UsuarioService usuarioService;
//    @Mock
//    private AlunoService alunoService;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(avaliacaoService, "objectMapper", objectMapper);
//    }
//
//    @Test
//    public void deveTestarCadastroAvaliacaoComSucesso() throws RegraDeNegocioException {
//        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
//        AvaliacaoEntity avaliacao = AvaliacaoFactory.getAvaliacaoFactory();
//        AvaliacaoCreateDTO avaliacaoDTO = AvaliacaoFactory.getAvaliacaoCreateDTOFactory();
//        UsernamePasswordAuthenticationToken dto
//                = new UsernamePasswordAuthenticationToken(1, Cargo.GESTOR, Collections.emptyList());
//        SecurityContextHolder.getContext().setAuthentication(dto);
//
//        when(alunoService.findById(anyInt())).thenReturn(AlunoFactory.getAlunoEntity());
//        when(usuarioService.getLoggedUser()).thenReturn(usuario);
//        when(acompanhamentoService.findById(anyInt())).thenReturn(AcompanhamentoFactory.getAcompanhamento());
//        when(avaliacaoRepository.save(any())).thenReturn(avaliacao);
//
//        AvaliacaoDTO avaliacaoDTO1 = avaliacaoService.cadastrarAvaliacao(avaliacaoDTO);
//
//        assertNotNull(avaliacaoDTO1);
//        assertEquals("Descrição Bala", avaliacaoDTO1.getDescricao());
//    }
//
//    @Test
//    public void deveTestarEditarAvaliacao() throws RegraDeNegocioException {
//        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoFactory();
//        AlunoEntity alunoEntity = AlunoFactory.getAlunoEntity();
//        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
//
//        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));
//        when(alunoService.findById(anyInt())).thenReturn(alunoEntity);
//        when(acompanhamentoService.findById(anyInt())).thenReturn(acompanhamentoEntity);
//        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity);
//
//        AvaliacaoDTO avaliacaoDTO = avaliacaoService.editarAvaliacao(1, AvaliacaoFactory.getEditarAvaliacaoDTOFactory());
//
//        assertNotNull(avaliacaoDTO);
//        assertEquals("Descrição Chiclete", avaliacaoDTO.getDescricao());
//    }
//
//    @Test
//    public void deveListarAvaliacaoPaginadoComSucesso() throws RegraDeNegocioException {
//        final int page = 0;
//        final int size = 1;
//
//        AvaliacaoEntity avaliacao = AvaliacaoFactory.getAvaliacaoFactory();
//        PageImpl<AvaliacaoEntity> lista = new PageImpl<>(List.of(avaliacao), PageRequest.of(page, size), 0);
//
//
//        when(avaliacaoRepository.findAll(any(PageRequest.class))).thenReturn(lista);
//        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPaginados(page, size);
//        assertNotNull(avaliacaoDTOPageDTO);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void DeveListarAvaliacaoPaginadoPorIDAlunoComListaErroDeValidacaoDosValoresDeSizeEpage() throws RegraDeNegocioException {
//        final int numeroPagina = -1;
//        final int tamanho = -1;
//        avaliacaoService.listarAvaliacoesPaginados(numeroPagina, tamanho);
//
//    }
//
//    @Test
//    public void deveListarAvaliacaoPaginadoComSizeZeradoComSucesso() throws RegraDeNegocioException {
//        final int page = 0;
//        final int size = 0;
//
//        List<AvaliacaoEntity> listaVazia = new ArrayList<>();
//        PageDTO lista = new PageDTO<>();
//        lista.setTotalElementos(0L);
//        lista.setQuantidadePaginas(0);
//        lista.setPagina(0);
//        lista.setTamanho(size);
//        lista.setElementos(listaVazia);
//
//
//        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPaginados(page, size);
//        assertNotNull(avaliacaoDTOPageDTO);
//    }
//
//    @Test
//    public void deveListarAvaliacaoPorAlunoPaginadoComSucesso() throws RegraDeNegocioException {
//        final int page = 0;
//        final int size = 1;
//
//        AvaliacaoEntity avaliacao = AvaliacaoFactory.getAvaliacaoFactory();
//        PageImpl<AvaliacaoEntity> lista = new PageImpl<>(List.of(avaliacao), PageRequest.of(page, size), 0);
//
//
//        when(avaliacaoRepository.findAllByIdAluno(anyInt(), any(PageRequest.class))).thenReturn(lista);
//        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPorAlunoPaginados(1, page, size);
//        assertNotNull(avaliacaoDTOPageDTO);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void DeveListarAvaliacaoPaginadoComListaErroDeValidacaoDosValoresDeSizeEpage() throws RegraDeNegocioException {
//        final int numeroPagina = -1;
//        final int tamanho = -1;
//        final int idAluno = 1;
//        avaliacaoService.listarAvaliacoesPorAlunoPaginados(idAluno, numeroPagina, tamanho);
//
//    }
//
//    @Test
//    public void deveListarAvaliacaoPorAlunoPaginadoComSizeZeradoComSucesso() throws RegraDeNegocioException {
//        final int page = 0;
//        final int size = 0;
//
//        List<AvaliacaoEntity> listaVazia = new ArrayList<>();
//        PageDTO lista = new PageDTO<>();
//        lista.setTotalElementos(0L);
//        lista.setQuantidadePaginas(0);
//        lista.setPagina(0);
//        lista.setTamanho(size);
//        lista.setElementos(listaVazia);
//
//
//        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPorAlunoPaginados(1, page, size);
//        assertNotNull(avaliacaoDTOPageDTO);
//    }
//
//    @Test
//    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
//        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoFactory();
//        Integer idAvaliacao = 1;
//        when(avaliacaoRepository.findById(avaliacaoEntity.getIdAvaliacao())).thenReturn(Optional.of(avaliacaoEntity));
//        AvaliacaoDTO avaliacaoDTO = avaliacaoService.findByIdDTO(idAvaliacao);
//
//        assertNotNull(avaliacaoDTO);
//
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveLancarRegradeNegocioExceptionAoNaoLocalizarAvaliacaoPorFindById() throws RegraDeNegocioException {
//        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.empty());
//        avaliacaoService.findById(1);
//    }
//}
