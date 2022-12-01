package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.AcompanhamentoFactory;
import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
import br.com.dbc.vemser.avaliaser.repositories.AcompanhamentoRepository;
import br.com.dbc.vemser.avaliaser.repositories.AlunoRepository;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.services.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AcompanhamentoServiceTest {

    @InjectMocks
    private AcompanhamentoService acompanhamentoService;
    @Mock
    private AcompanhamentoRepository acompanhamentoRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(acompanhamentoService, "objectMapper", objectMapper);
    }


    @Test
    public void DeveListarAcompanhamentosPaginadoCorretamente() {
        final int numeroPagina = 0;
        final int tamanho = 3;

        AcompanhamentoEntity acompanhamento = AcompanhamentoFactory.getAcompanhamento();
        PageImpl<AcompanhamentoEntity> listaPaginada = new PageImpl<>(List.of(acompanhamento), PageRequest.of(numeroPagina, tamanho), 0);

        when(acompanhamentoRepository.findAll(any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<AcompanhamentoDTO> acompanhamentoDTOPageDTO = acompanhamentoService.listarAcompanhamentosPaginados(numeroPagina, tamanho);

        assertNotNull(acompanhamentoDTOPageDTO);
        assertEquals(1, acompanhamentoDTOPageDTO.getTotalElementos());
        assertEquals(1, acompanhamentoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), acompanhamentoDTOPageDTO.getPagina());
    }
    @Test
    public void deveTestarCadastroAcompanhamentosComSucesso() throws RegraDeNegocioException, IOException {


        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
        AcompanhamentoCreateDTO acompanhamentoCreateDTO = AcompanhamentoFactory.getAcompanhamentoCreateDTO();

        when(acompanhamentoRepository.save(any())).thenReturn(acompanhamentoEntity);

        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.cadastrarAcompanhamento(acompanhamentoCreateDTO);

        assertEquals(acompanhamentoEntity.getIdAcompanhamento(), acompanhamentoDTO.getIdAcompanhamento());
        assertEquals(acompanhamentoEntity.getTitulo(), acompanhamentoDTO.getTitulo());
        assertEquals(acompanhamentoEntity.getDataInicio(), acompanhamentoDTO.getDataInicio());
        assertNotNull(acompanhamentoDTO);
    }

    @Test
    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
        Integer idAcompanhamento = 1;

        when(acompanhamentoRepository.findById(acompanhamentoEntity.getIdAcompanhamento())).thenReturn(Optional.of(acompanhamentoEntity));
        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.findByIdDTO(idAcompanhamento);

        assertNotNull(acompanhamentoDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer idAcompanhamento = 1;
        when(acompanhamentoRepository.findById(anyInt())).thenReturn(Optional.empty());
        acompanhamentoService.findById(idAcompanhamento);

    }
    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException, IOException {
        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
        Integer idAcompanhamento = 1;
        when(acompanhamentoRepository.findById(idAcompanhamento)).thenReturn(Optional.of(acompanhamentoEntity));
        AcompanhamentoEntity acompanhamento  = acompanhamentoService.findById(idAcompanhamento);

        assertNotNull(acompanhamento);
    }
    @Test
    public void deveTestarAtualizarAcompanhamentoComSucesso() throws RegraDeNegocioException, IOException {

        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoFactory.getAcompanhamento();
        EditarAcompanhamentoDTO editarAcompanhamentoDTO = AcompanhamentoFactory.getEditarAcompanhamento();


        when(acompanhamentoRepository.findById(anyInt())).thenReturn(Optional.of(acompanhamentoEntity));
        when(acompanhamentoRepository.save(any())).thenReturn(acompanhamentoEntity);

        AcompanhamentoDTO acompanhamentoDTO = acompanhamentoService.editarAcompanhamento(editarAcompanhamentoDTO,1);

        assertNotNull(acompanhamentoDTO);
    }

}

