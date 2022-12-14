//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.EditarFeedBackDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
//import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
//import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
//import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
//import br.com.dbc.vemser.avaliaser.factory.FeedBackFactory;
//import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
//import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
//import br.com.dbc.vemser.avaliaser.repositories.avaliaser.FeedBackRepository;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.FeedbackService;
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
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.io.IOException;
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
//public class FeedBackServiceTest {
//    @InjectMocks
//    private FeedbackService feedbackService;
//    @Mock
//    private FeedBackRepository feedBackRepository;
//    @Mock
//    private UsuarioService usuarioService;
//    @Mock
//    private AlunoService alunoService;
//    @Mock
//    private AlunoRepository alunoRepository;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(feedbackService, "objectMapper", objectMapper);
//    }
//
//
//    @Test
//    public void DeveListarFeedBackPaginadoCorretamente() throws RegraDeNegocioException {
//        final int numeroPagina = 0;
//        final int tamanho = 3;
//        AlunoEntity alunoEntity = AlunoFactory.getAlunoEntity();
//        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
//        feedBackEntity.setAlunoEntity(alunoEntity);
//        PageImpl<FeedBackEntity> listaPaginada = new PageImpl<>(List.of(feedBackEntity), PageRequest.of(numeroPagina, tamanho), 0);
//
//        when(feedBackRepository.findAll(any(Pageable.class))).thenReturn(listaPaginada);
//        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPaginados(numeroPagina, tamanho);
//
//        assertNotNull(feedBackDTOPageDTO);
//        assertEquals(1, feedBackDTOPageDTO.getTotalElementos());
//        assertEquals(1, feedBackDTOPageDTO.getQuantidadePaginas());
//        assertEquals(listaPaginada.getPageable().getPageNumber(), feedBackDTOPageDTO.getPagina());
//    }
//
//    @Test
//    public void DeveListarFeedBackPorIDAlunoPaginadoCorretamente() throws RegraDeNegocioException {
//
//        final int numeroPagina = 0;
//        final int tamanho = 3;
//
//        AlunoEntity alunoEntity = AlunoFactory.getAlunoEntity();
//        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
//        PageImpl<FeedBackEntity> listaPaginada = new PageImpl<>(List.of(feedBackEntity), PageRequest.of(numeroPagina, tamanho), 0);
//
//        when(feedBackRepository.findAllByIdAluno(anyInt(), any(Pageable.class))).thenReturn(listaPaginada);
//        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPorAlunoPaginados(alunoEntity.getIdAluno(), numeroPagina, tamanho);
//
//        assertNotNull(feedBackDTOPageDTO);
//        assertEquals(1, feedBackDTOPageDTO.getTotalElementos());
//        assertEquals(1, feedBackDTOPageDTO.getQuantidadePaginas());
//        assertEquals(listaPaginada.getPageable().getPageNumber(), feedBackDTOPageDTO.getPagina());
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void DeveListarFeedBackPaginadoComListaErroDeValidacaoDosValoresDeSizeEpage() throws RegraDeNegocioException {
//        final int numeroPagina = -1;
//        final int tamanho = -1;
//        feedbackService.listarFeedBackPaginados(numeroPagina, tamanho);
//
//    }
//
//    @Test
//    public void DeveListarFeedBackPaginadoPorIdAlunoComListaVazia() throws RegraDeNegocioException {
//        final int numeroPagina = 0;
//        final int tamanho = 0;
//        List<FeedBackDTO> listaVazia = new ArrayList<>();
//        PageDTO<FeedBackDTO> feedBackDTOPageDTO = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
//
//        PageDTO<FeedBackDTO> paginaRecebida = feedbackService.listarFeedBackPorAlunoPaginados(1, numeroPagina, tamanho);
//        assertEquals(paginaRecebida, feedBackDTOPageDTO);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void DeveListarAvaliacaoPaginadoComListaErroDeValidacaoDosValoresDeSizeEpage() throws RegraDeNegocioException {
//        final int numeroPagina = -1;
//        final int tamanho = -1;
//        final int idAluno = 1;
//        feedbackService.listarFeedBackPorAlunoPaginados(idAluno, numeroPagina, tamanho);
//    }
//
//    @Test
//    public void deveListarFeedBackPorAlunoPaginadoComSizeEpageZeradoComSucesso() throws RegraDeNegocioException {
//        final int page = 0;
//        final int size = 0;
//
//        List<FeedBackEntity> listaVazia = new ArrayList<>();
//        PageDTO lista = new PageDTO<>();
//        lista.setTotalElementos(0L);
//        lista.setQuantidadePaginas(0);
//        lista.setPagina(0);
//        lista.setTamanho(size);
//        lista.setElementos(listaVazia);
//
//
//        PageDTO<FeedBackDTO> avaliacaoDTOPageDTO = feedbackService.listarFeedBackPorAlunoPaginados(1, page, size);
//        assertNotNull(avaliacaoDTOPageDTO);
//    }
//
//    @Test
//    public void deveListarFeedBackPorPaginadoComSizeEpageZeradoComSucesso() throws RegraDeNegocioException {
//        final int page = 0;
//        final int size = 0;
//
//        List<FeedBackEntity> listaVazia = new ArrayList<>();
//        PageDTO lista = new PageDTO<>();
//        lista.setTotalElementos(0L);
//        lista.setQuantidadePaginas(0);
//        lista.setPagina(0);
//        lista.setTamanho(size);
//        lista.setElementos(listaVazia);
//
//
//        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPaginados(page, size);
//        assertNotNull(feedBackDTOPageDTO);
//        assertEquals(0, feedBackDTOPageDTO.getElementos().size());
//    }
//
//
//    @Test
//    public void deveTestarCadastroFeedBacksComSucesso() throws RegraDeNegocioException {
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
//        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
//        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
//        feedBackEntity.setAlunoEntity(aluno);
//        FeedBackCreateDTO feedBackCreateDTO = FeedBackFactory.getFeedBackCreateDTO();
//
//        when(usuarioService.getLoggedUser()).thenReturn(usuario);
//        when(alunoService.findById(anyInt())).thenReturn(aluno);
//        when(feedBackRepository.save(any())).thenReturn(feedBackEntity);
//
//        FeedBackDTO feedBackDTO = feedbackService.cadastrarFeedBack(feedBackCreateDTO);
//
//        assertEquals(feedBackEntity.getIdFeedBack(), feedBackDTO.getIdFeedBack());
//        assertEquals(feedBackEntity.getTipo(), feedBackDTO.getTipo());
//        assertEquals(feedBackEntity.getDescricao(), feedBackDTO.getDescricao());
//        assertEquals(feedBackEntity.getIdAluno(), feedBackDTO.getAlunoDTO().getIdAluno());
//        assertNotNull(feedBackEntity);
//    }
//
//    @Test
//    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
//        feedBackEntity.setAlunoEntity(aluno);
//        Integer idFeedback = 1;
//
//        when(feedBackRepository.findById(feedBackEntity.getIdFeedBack())).thenReturn(Optional.of(feedBackEntity));
//        FeedBackDTO feedBackDTO = feedbackService.findByIdDTO(idFeedback);
//
//        assertNotNull(feedBackDTO);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
//        Integer idFeedback = 1;
//        when(feedBackRepository.findById(anyInt())).thenReturn(Optional.empty());
//        feedbackService.findById(idFeedback);
//
//    }
//
//    @Test
//    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException{
//        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
//        Integer idFeedback = 1;
//        when(feedBackRepository.findById(idFeedback)).thenReturn(Optional.of(feedBackEntity));
//        FeedBackEntity feedBackEntityRetorno = feedbackService.findById(idFeedback);
//
//        assertNotNull(feedBackEntityRetorno);
//    }
//
//    @Test
//    public void deveTestarEditarFeedBackComSucesso() throws RegraDeNegocioException, IOException {
//
//        FeedBackDTO feedBackDTO = FeedBackFactory.getFeedBackDTO();
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
//        feedBackEntity.setAlunoEntity(aluno);
//        EditarFeedBackDTO editarFeedBackDTO = FeedBackFactory.getEditarFeedBack();
//        editarFeedBackDTO.setIdAluno(aluno.getIdAluno());
//
//        when(feedBackRepository.findById(editarFeedBackDTO.getIdAluno())).thenReturn(Optional.of(feedBackEntity));
//        when(alunoService.findById(anyInt())).thenReturn(aluno);
//        when(feedBackRepository.save(any())).thenReturn(feedBackEntity);
//
//
//        FeedBackDTO feedBackDTO1 = feedbackService.editarFeedBack(aluno.getIdAluno(), editarFeedBackDTO);
//
//        assertNotNull(feedBackDTO1);
//    }
//
//    private static UsernamePasswordAuthenticationToken getAuthentication() {
//        return new UsernamePasswordAuthenticationToken(1,
//                null,
//                Collections.emptyList());
//    }
//
//
//}
//
