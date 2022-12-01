package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.*;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.enums.Tipo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
import br.com.dbc.vemser.avaliaser.factory.FeedBackFactory;
import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
import br.com.dbc.vemser.avaliaser.repositories.AcompanhamentoRepository;
import br.com.dbc.vemser.avaliaser.repositories.FeedBackRepository;
import br.com.dbc.vemser.avaliaser.services.AcompanhamentoService;
import br.com.dbc.vemser.avaliaser.services.AlunoService;
import br.com.dbc.vemser.avaliaser.services.FeedbackService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedBackServiceTest {
    @InjectMocks
    private FeedbackService feedbackService;
    @Mock
    private FeedBackRepository feedBackRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private AlunoService alunoService;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(feedbackService, "objectMapper", objectMapper);
    }


    @Test
    public void DeveListarFeedBackPaginadoCorretamente() {
        final int numeroPagina = 0;
        final int tamanho = 3;

        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
        PageImpl<FeedBackEntity> listaPaginada = new PageImpl<>(List.of(feedBackEntity), PageRequest.of(numeroPagina, tamanho), 0);

        when(feedBackRepository.findAll(any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPaginados(numeroPagina, tamanho);

        assertNotNull(feedBackDTOPageDTO);
        assertEquals(1, feedBackDTOPageDTO.getTotalElementos());
        assertEquals(1, feedBackDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), feedBackDTOPageDTO.getPagina());
    }

    @Test
    public void DeveListarFeedBackPorIDAlunoPaginadoCorretamente() {
        final int numeroPagina = 0;
        final int tamanho = 3;

        AlunoEntity alunoEntity = AlunoFactory.getAlunoEntity();
        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
        PageImpl<FeedBackEntity> listaPaginada = new PageImpl<>(List.of(feedBackEntity), PageRequest.of(numeroPagina, tamanho), 0);

        when(feedBackRepository.findAllByIdAluno(anyInt(),any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<FeedBackDTO> feedBackDTOPageDTO = feedbackService.listarFeedBackPorAlunoPaginados(alunoEntity.getIdAluno(),numeroPagina, tamanho);

        assertNotNull(feedBackDTOPageDTO);
        assertEquals(1, feedBackDTOPageDTO.getTotalElementos());
        assertEquals(1, feedBackDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), feedBackDTOPageDTO.getPagina());
    }
    @Test
    public void deveTestarCadastroFeedBacksComSucesso() throws RegraDeNegocioException, IOException {
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        UsuarioEntity usuario = UsuarioFactory.getUsuarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
        FeedBackCreateDTO feedBackCreateDTO = FeedBackFactory.getFeedBackCreateDTO();

        when(alunoService.findById(anyInt())).thenReturn(aluno);
        when(usuarioService.getLoggedUser()).thenReturn(usuario);
        when(feedBackRepository.save(any())).thenReturn(feedBackEntity);

        FeedBackDTO feedBackDTO = feedbackService.cadastrarFeedBack(feedBackCreateDTO);

        assertEquals(feedBackEntity.getIdFeedBack(), feedBackDTO.getIdFeedBack());
        assertEquals(feedBackEntity.getTipo(), feedBackDTO.getTipo());
        assertEquals(feedBackEntity.getDescricao(), feedBackDTO.getDescricao());
        assertEquals(feedBackEntity.getIdAluno(), feedBackDTO.getIdAluno());
        assertEquals(feedBackEntity.getIdUsuario(), feedBackDTO.getIdUsuario());
        assertNotNull(feedBackEntity);
    }

    @Test
    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
        Integer idFeedback = 1;

        when(feedBackRepository.findById(feedBackEntity.getIdFeedBack())).thenReturn(Optional.of(feedBackEntity));
        FeedBackDTO feedBackDTO = feedbackService.findByIdDTO(idFeedback);

        assertNotNull(feedBackDTO);
    }
//
    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer idFeedback = 1;
        when(feedBackRepository.findById(anyInt())).thenReturn(Optional.empty());
        feedbackService.findById(idFeedback);

    }
    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException, IOException {
        FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
        Integer idFeedback = 1;
        when(feedBackRepository.findById(idFeedback)).thenReturn(Optional.of(feedBackEntity));
        FeedBackEntity feedBackEntityRetorno  = feedbackService.findById(idFeedback);

        assertNotNull(feedBackEntityRetorno);
    }
    @Test
    public void deveTestarEditarFeedBackComSucesso() throws RegraDeNegocioException {

       FeedBackEntity feedBackEntity = FeedBackFactory.getFeedBack();
       EditarFeedBackDTO editarFeedBackDTO = FeedBackFactory.getEditarFeedBack();

       when(feedBackRepository.findById(anyInt())).thenReturn(Optional.of(feedBackEntity));
       when(feedBackRepository.save(any())).thenReturn(feedBackEntity);

       FeedBackDTO feedBackDTO = feedbackService.editarFeedBack(1, editarFeedBackDTO);

       assertNotNull(feedBackDTO);
    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }


}

