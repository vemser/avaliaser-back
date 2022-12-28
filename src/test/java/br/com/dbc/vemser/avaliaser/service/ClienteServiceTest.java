package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.ClienteFactory;
import br.com.dbc.vemser.avaliaser.repositories.allocation.ClienteRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ClienteService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {
    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSalvarComSucesso() {
        ClienteEntity cliente = ClienteFactory.getClienteEntity();
        ClienteCreateDTO clienteCreateDTO = ClienteFactory.getClienteCreateDTO();
        when(clienteRepository.save(any())).thenReturn(cliente);
        ClienteDTO clienteDTO = clienteService.salvar(clienteCreateDTO);

        assertNotNull(clienteDTO);
        assertEquals("Coca Cola", clienteDTO.getNome());

    }

    @Test
    public void deveTestarListarComSucesso() throws RegraDeNegocioException {
        Integer pagina = 4;
        Integer quantidade = 10;

        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();

        Page<ClienteEntity> clienteEntityPage = new PageImpl<>(List.of(clienteEntity));

        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(clienteEntityPage);
        PageDTO<ClienteDTO> clienteDTOPageDTO = clienteService.listar(pagina, quantidade);

        assertNotNull(clienteDTOPageDTO);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();
        ClienteCreateDTO clienteCreateDTO = ClienteFactory.getClienteCreateDTO();
        Integer id = 1;
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(clienteEntity));
        ClienteEntity cliente = ClienteFactory.getClienteEntity();
        when(clienteRepository.save(any())).thenReturn(cliente);

        ClienteDTO clienteDTO = clienteService.editar(id, clienteCreateDTO);
        assertNotNull(clienteDTO);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ClienteEntity cliente = ClienteFactory.getClienteEntity();
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        clienteService.deletar(id);

        verify(clienteRepository, times(1)).delete(any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(clienteEntity));

        ClienteEntity cliente = clienteService.findById(id);

        assertNotNull(cliente);
        assertNotNull(cliente.getIdCliente());
        assertEquals(Optional.of(1), cliente.getIdCliente());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer busca = 10;
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.empty());

        ClienteEntity desafio = clienteService.findById(busca);

        assertNull(desafio);
    }

    @Test
    public void deveTestarListarPorEmailComSucesso() throws RegraDeNegocioException {
        Integer pagina = 4;
        Integer quantidade = 10;
        String email = "cocacolabr@mail.com.br";

        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();

        Page<ClienteEntity> clienteEntityPage = new PageImpl<>(List.of(clienteEntity));

        when(clienteRepository.findAllByEmailContainingIgnoreCaseAndAtivo(Ativo.S, any(Pageable.class), anyString())).thenReturn(clienteEntityPage);

        PageDTO<ClienteDTO> clienteDTOPageDTO = clienteService.listarPorEmail(pagina, quantidade, email);

        assertNotNull(clienteDTOPageDTO);
    }

    @Test
    public void deveTestarListarPorNomeComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer pagina = 4;
        Integer quantidade = 10;
        String email = "cocacolabr@mail.com.br";

        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();

        Page<ClienteEntity> clienteEntityPage = new PageImpl<>(List.of(clienteEntity));

        when(clienteRepository.findAllByNomeContainingIgnoreCaseAndAtivo(Ativo.S,any(Pageable.class), anyString())).thenReturn(clienteEntityPage);
        //ACT
        PageDTO<ClienteDTO> clienteDTOPageDTO = clienteService.listarPorNome(pagina, quantidade, email);

        //ASSERT
        assertNotNull(clienteDTOPageDTO);
    }

    @Test
    public void deveTestarFindByEmailComSucesso() throws RegraDeNegocioException {
        String email = "cocacolabr@mail.com.br";
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();
        when(clienteRepository.findByEmailIgnoreCaseAndAtivo(Ativo.S,anyString())).thenReturn(Optional.of(clienteEntity));
        ClienteEntity cliente = clienteService.findByEmail(email);

        assertNotNull(cliente);
    }
}