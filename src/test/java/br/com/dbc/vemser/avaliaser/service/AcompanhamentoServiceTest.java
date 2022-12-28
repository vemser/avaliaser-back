//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.EditarAcompanhamentoDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
//import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.factory.AcompanhamentoFactory;
//import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AcompanhamentoRepository;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.AcompanhamentoService;
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
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.ArrayList;
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
//public class AcompanhamentoServiceTest {
//
//    @InjectMocks
//    private AcompanhamentoService acompanhamentoService;
//    @Mock
//    private AcompanhamentoRepository acompanhamentoRepository;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(acompanhamentoService, "objectMapper", objectMapper);
//    }
//
//    @Test
//    public void deveListarAcompanhamentosPaginadoCorretamente() throws RegraDeNegocioException {
//        final int numeroPagina = 0;
//        final int tamanho = 3;
//
//        AcompanhamentoEntity acompanhamento = AcompanhamentoFactory.getAcompanhamento();
//        PageImpl<AcompanhamentoEntity> listaPaginada = new PageImpl<>(List.of(acompanhamento), PageRequest.of(numeroPagina, tamanho), 0);
//
//        when(acompanhamentoRepository.findAll(any(Pageable.class))).thenReturn(listaPaginada);
//        PageDTO<AcompanhamentoDTO> acompanhamentoDTOPageDTO = acompanhamentoService.listarAcompanhamentosPaginados(numeroPagina, tamanho);
//
//        assertNotNull(acompanhamentoDTOPageDTO);
//        assertEquals(1, acompanhamentoDTOPageDTO.getTotalElementos());
//        assertEquals(1, acompanhamentoDTOPageDTO.getQuantidadePaginas());
//        assertEquals(listaPaginada.getPageable().getPageNumber(), acompanhamentoDTOPageDTO.getPagina());
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void DeveListarAcompanhamentoPaginadoComListaErroDeValidacaoDosValoresDeSizeEpage() throws RegraDeNegocioException {
//        final int numeroPagina = -1;
//        final int tamanho = -1;
//        acompanhamentoService.listarAcompanhamentosPaginados(numeroPagina, tamanho);
//
//    }
//
//    @Test
//    public void DeveListarAcompanhamentoPaginadoComListaVazia() throws RegraDeNegocioException {
//        final int numeroPagina = 0;
//        final int tamanho = 0;
//        List<AcompanhamentoDTO> listaVazia = new ArrayList<>();
//        PageDTO<AcompanhamentoDTO> acompanhamentoDTOPageDTO = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
//
//        PageDTO<AcompanhamentoDTO> paginaRecebida = acompanhamentoService.listarAcompanhamentosPaginados(numeroPagina, tamanho);
//        assertEquals(paginaRecebida, acompanhamentoDTOPageDTO);
//    }
//
//    @Test
//    public void deveTestarCadastroAcompanhamentosComSucesso() throws RegraDeNegocioException {
//
//
//        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
//        AcompanhamentoCreateDTO acompanhamentoCreateDTO = AcompanhamentoFactory.getAcompanhamentoCreateDTO();
//
//        when(acompanhamentoRepository.save(any())).thenReturn(acompanhamentoEntity);
//
//        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.cadastrarAcompanhamento(acompanhamentoCreateDTO);
//
//        assertEquals(acompanhamentoEntity.getIdAcompanhamento(), acompanhamentoDTO.getIdAcompanhamento());
//        assertEquals(acompanhamentoEntity.getTitulo(), acompanhamentoDTO.getTitulo());
//        assertEquals(acompanhamentoEntity.getDataInicio(), acompanhamentoDTO.getDataInicio());
//        assertNotNull(acompanhamentoDTO);
//    }
//
//    @Test
//    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
//        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
//        Integer idAcompanhamento = 1;
//
//        when(acompanhamentoRepository.findById(acompanhamentoEntity.getIdAcompanhamento())).thenReturn(Optional.of(acompanhamentoEntity));
//        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.findByIdDTO(idAcompanhamento);
//
//        assertNotNull(acompanhamentoDTO);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
//        Integer idAcompanhamento = 1;
//        when(acompanhamentoRepository.findById(anyInt())).thenReturn(Optional.empty());
//        acompanhamentoService.findById(idAcompanhamento);
//
//    }
//
//    @Test
//    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
//        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
//        Integer idAcompanhamento = 1;
//        when(acompanhamentoRepository.findById(idAcompanhamento)).thenReturn(Optional.of(acompanhamentoEntity));
//        AcompanhamentoEntity acompanhamento = acompanhamentoService.findById(idAcompanhamento);
//
//        assertNotNull(acompanhamento);
//    }
//
//    @Test
//    public void deveTestarAtualizarAcompanhamentoComSucesso() throws RegraDeNegocioException {
//
//        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
//        EditarAcompanhamentoDTO editarAcompanhamentoDTO = AcompanhamentoFactory.getEditarAcompanhamento();
//
//
//        when(acompanhamentoRepository.findById(anyInt())).thenReturn(Optional.of(acompanhamentoEntity));
//        when(acompanhamentoRepository.save(any())).thenReturn(acompanhamentoEntity);
//
//        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.editarAcompanhamento(editarAcompanhamentoDTO, 1);
//
//        assertNotNull(acompanhamentoDTO);
//    }
//
//}
//
