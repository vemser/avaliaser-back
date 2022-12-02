package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.AcompanhamentoFactory;
import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
import br.com.dbc.vemser.avaliaser.factory.AvaliacaoFactory;
import br.com.dbc.vemser.avaliaser.repositories.AvaliacaoRepository;
import br.com.dbc.vemser.avaliaser.services.AcompanhamentoService;
import br.com.dbc.vemser.avaliaser.services.AlunoService;
import br.com.dbc.vemser.avaliaser.services.AvaliacaoService;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;
    @Mock
    private AcompanhamentoService acompanhamentoService;
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
        ReflectionTestUtils.setField(avaliacaoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCadastroAvaliacaoComSucesso() throws RegraDeNegocioException {
        AvaliacaoEntity avaliacao = AvaliacaoFactory.getAvaliacaoFactory();
        AvaliacaoCreateDTO avaliacaoDTO = AvaliacaoFactory.getAvaliacaoCreateDTOFactory();

        when(alunoService.findById(anyInt())).thenReturn(AlunoFactory.getAlunoEntity());
        when(acompanhamentoService.findById(anyInt())).thenReturn(AcompanhamentoFactory.getAcompanhamento());
        when(avaliacaoRepository.save(any())).thenReturn(avaliacao);

        AvaliacaoDTO avaliacaoDTO1 = avaliacaoService.cadastrarAvaliacao(avaliacaoDTO);

        assertNotNull(avaliacaoDTO1);
        assertEquals("Descrição Bala",avaliacaoDTO1.getDescricao());
    }

    @Test
    public void deveTestarEditarAvaliacao() throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvaliacaoFactory();
        AlunoEntity alunoEntity = AlunoFactory.getAlunoEntity();
        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();

        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));
        when(alunoService.findById(anyInt())).thenReturn(alunoEntity);
        when(acompanhamentoService.findById(anyInt())).thenReturn(acompanhamentoEntity);
        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity);

        AvaliacaoDTO avaliacaoDTO = avaliacaoService.editarAvaliacao(1, AvaliacaoFactory.getEditarAvaliacaoDTOFactory());

        assertNotNull(avaliacaoDTO);
        assertEquals("Descrição Chiclete", avaliacaoDTO.getDescricao());
    }

    @Test
    public void deveListarAvaliacaoPaginadoComSucesso() throws RegraDeNegocioException {
         final int page = 0;
         final int size = 1;

         AvaliacaoEntity avaliacao = AvaliacaoFactory.getAvaliacaoFactory();
         PageImpl<AvaliacaoEntity> lista = new PageImpl<>(List.of(avaliacao), PageRequest.of(page, size), 0);


         when(avaliacaoRepository.findAll(any(PageRequest.class))).thenReturn(lista);
         PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPaginados(page, size);
         assertNotNull(avaliacaoDTOPageDTO);
    }

    @Test
    public void deveListarAvaliacaoPaginadoComSizeZeradoComSucesso() throws RegraDeNegocioException {
        final int page = 0;
        final int size = 0;

        List<AvaliacaoEntity> listaVazia = new ArrayList<>();
        PageDTO lista = new PageDTO<>();
        lista.setTotalElementos(0L);
        lista.setQuantidadePaginas(0);
        lista.setPagina(0);
        lista.setTamanho(size);
        lista.setElementos(listaVazia);


        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPaginados(page, size);
        assertNotNull(avaliacaoDTOPageDTO);
    }

    @Test
    public void deveListarAvaliacaoPorAlunoPaginadoComSucesso() throws RegraDeNegocioException {
        final int page = 0;
        final int size = 1;

        AvaliacaoEntity avaliacao = AvaliacaoFactory.getAvaliacaoFactory();
        PageImpl<AvaliacaoEntity> lista = new PageImpl<>(List.of(avaliacao), PageRequest.of(page, size), 0);


        when(avaliacaoRepository.findAllByIdAluno(anyInt(),any(PageRequest.class))).thenReturn(lista);
        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPorAlunoPaginados(1, page, size);
        assertNotNull(avaliacaoDTOPageDTO);
    }

    @Test
    public void deveListarAvaliacaoPorAlunoPaginadoComSizeZeradoComSucesso() throws RegraDeNegocioException {
        final int page = 0;
        final int size = 0;

        List<AvaliacaoEntity> listaVazia = new ArrayList<>();
        PageDTO lista = new PageDTO<>();
        lista.setTotalElementos(0L);
        lista.setQuantidadePaginas(0);
        lista.setPagina(0);
        lista.setTamanho(size);
        lista.setElementos(listaVazia);


        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarAvaliacoesPorAlunoPaginados(1, page, size);
        assertNotNull(avaliacaoDTOPageDTO);
    }
}
