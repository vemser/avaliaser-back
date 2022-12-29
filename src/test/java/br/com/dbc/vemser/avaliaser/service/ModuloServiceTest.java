package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloTrilhaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaCreateDTO;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.ModuloRepository;
import br.com.dbc.vemser.avaliaser.services.vemrankser.ModuloService;
import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ModuloServiceTest {

    @InjectMocks
    private ModuloService moduloService;
    @Mock
    private ModuloRepository moduloRepository;

    @Mock
    private TrilhaService trilhaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(moduloService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarComSucesso() throws RegraDeNegocioException {
        ModuloCreateDTO moduloCreateDTO = getModuloCreateDTO();
        when(moduloRepository.save(any())).thenReturn(getModuloEntity(1));
        ModuloDTO moduloDTO1 = moduloService.criar(moduloCreateDTO);
        assertNotNull(moduloDTO1);

    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        ModuloCreateDTO moduloCreateDTO = getModuloCreateDTO();
        ModuloEntity moduloEntity = getModuloEntity(1);
        Integer id = 1;
        when(moduloRepository.findById(anyInt())).thenReturn(Optional.of(moduloEntity));
        when(moduloRepository.save(any())).thenReturn(moduloEntity);

        ModuloDTO moduloDTO = moduloService.editar(id, moduloCreateDTO);
        assertNotNull(moduloDTO);
        assertEquals(id, moduloDTO.getIdModulo());

    }

    @Test
    public void deveTestarDesativarComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ModuloEntity moduloEntity = getModuloEntity(1);
        moduloEntity.setAtivo(Ativo.N);
        when(moduloRepository.findById(anyInt())).thenReturn(Optional.of(moduloEntity));
        when(moduloRepository.save(any())).thenReturn(moduloEntity);
        moduloService.desativar(id);
        moduloRepository.save(moduloEntity);
        assertNotEquals(Ativo.S,moduloEntity.getAtivo());

    }

    @Test
    public void deveTestarBuscarPorIDModulo() throws RegraDeNegocioException {
        ModuloEntity moduloEntity = getModuloEntity(1);
        Integer idModulo = 1;
        when(moduloRepository.findById(any())).thenReturn(Optional.of(moduloEntity));
        moduloService.findById(idModulo);
    }

    @Test
    public void deveTestarListarModulo() {
        ModuloEntity moduloEntity = getModuloEntity(1);
        Integer page = 1;
        Integer size = 10;
        Page<ModuloEntity> moduloEntitiesPage = new PageImpl<>(List.of(moduloEntity));
        when(moduloRepository.findAll(any(Pageable.class))).thenReturn(moduloEntitiesPage);
        PageDTO<ModuloDTO> moduloDTOPageDTO = moduloService.listarModulo(page, size);
        assertNotNull(moduloDTOPageDTO);
    }

    @Test
    public void deveTestarVincularModuloTrilha() throws RegraDeNegocioException {
        ModuloEntity moduloEntity = getModuloEntity(1);
        moduloEntity.setIdModulo(1);
        TrilhaEntity trilhaEntity = new TrilhaEntity();
        trilhaEntity.setIdTrilha(1);
        when(moduloRepository.findById(anyInt())).thenReturn(Optional.of(moduloEntity));
        when(trilhaService.findById(anyInt())).thenReturn(trilhaEntity);
        ModuloDTO moduloDTO = moduloService.vincularModuloTrilha(moduloEntity.getIdModulo(), trilhaEntity.getIdTrilha());
        assertNotNull(moduloDTO);

    }

    @Test
    public void deveTestarFindAllComSucesso() {
        List<ModuloEntity> moduloEntityList = new ArrayList<>();
        ModuloEntity moduloEntity = getModuloEntity(1);
        moduloEntityList.add(moduloEntity);
        when(moduloRepository.findAll()).thenReturn(moduloEntityList);
        List<ModuloDTO> moduloDTOS = moduloService.listAllModulos();

        assertNotNull(moduloDTOS);
        assertEquals(1, moduloDTOS.size());
    }
    @Test
    public void deveTestarClonarComSucesso(){

    }


    private ModuloEntity getModuloEntity(Integer idModulo) {
        ModuloEntity moduloEntity = new ModuloEntity();
        moduloEntity.setIdModulo(idModulo);
        moduloEntity.setAtivo(Ativo.S);
        moduloEntity.setNome("alok");
        moduloEntity.setDataInicio(LocalDate.now());
        moduloEntity.setDataFim(LocalDate.now().plusDays(1));
        return moduloEntity;
    }

    private ModuloDTO getModuloDTO() {
        ModuloDTO moduloDTO = new ModuloDTO();
        moduloDTO.setIdModulo(1);
        moduloDTO.setNome("jonas");
        moduloDTO.setDataInicio(LocalDate.now());
        moduloDTO.setDataFim(LocalDate.now().plusDays(2));
        return moduloDTO;
    }

    private ModuloCreateDTO getModuloCreateDTO() {
        ModuloCreateDTO moduloCreateDTO = new ModuloCreateDTO();
        moduloCreateDTO.setNome("jonas");
        moduloCreateDTO.setDataInicio(LocalDate.now());
        moduloCreateDTO.setDataFim(LocalDate.now().plusDays(1));
        return moduloCreateDTO;
    }

    private static TrilhaEntity getTrilhaEntity(Integer idTrilha) {
        TrilhaEntity trilhaEntity = new TrilhaEntity();
        trilhaEntity.setIdTrilha(idTrilha);
        trilhaEntity.setNome("BACKEND");
        return trilhaEntity;
    }

    private static TrilhaCreateDTO getTrilhaCreateDTO() {
        TrilhaCreateDTO trilhaCreateDTO = new TrilhaCreateDTO();
        trilhaCreateDTO.setNome("QA");
        return trilhaCreateDTO;
    }

    private static ModuloTrilhaDTO getModuloTrilhaDTO() {
        ModuloTrilhaDTO moduloTrilhaDTO = new ModuloTrilhaDTO();
        moduloTrilhaDTO.setIdModulo(1);
        moduloTrilhaDTO.setIdTrilha(1);
        return moduloTrilhaDTO;
    }
}

