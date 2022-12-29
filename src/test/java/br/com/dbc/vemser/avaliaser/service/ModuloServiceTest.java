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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

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
    public void deveTestarCriarComSucesso() {

        ModuloCreateDTO moduloCreateDTO = getModuloCreateDTO();

        when(moduloRepository.save(any())).thenReturn(getModuloEntity(1));

        ModuloDTO moduloDTO1 = moduloService.criar(moduloCreateDTO);

        assertNotNull(moduloDTO1);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        Integer idModulo = 11;
        ModuloCreateDTO moduloCreateDTO = getModuloCreateDTO();
        ModuloEntity moduloEntity = getModuloEntity(idModulo);

        when(moduloRepository.findById(any())).thenReturn(Optional.of(moduloEntity));
        when(moduloRepository.save(any())).thenReturn(getModuloEntity(11));

        ModuloDTO moduloDTO1 = moduloService.editar(idModulo, moduloCreateDTO);


        assertNotNull(moduloDTO1);
        assertEquals(11, moduloDTO1.getIdModulo());

    }

//    @Test
//    public void deveTestarDesativarComSucesso() throws RegraDeNegocioException {
//        Integer idModulo = 11;
//        ModuloEntity moduloEntity = getModuloEntity(idModulo);
//
////        when(moduloRepository.save(any())).thenReturn(getModuloEntity(1));
//
//        moduloService.desativar(idModulo);
//
//
////        assertNotNull();
//
//    }

    @Test
    public void deveTestarConverterEntityComSucesso() throws RegraDeNegocioException {
        Integer idModulo = 11;
        ModuloEntity moduloEntity = getModuloEntity(idModulo);
        ModuloDTO moduloDTO = getModuloDTO();

//        when(moduloRepository.save(any())).thenReturn(getModuloEntity(1));
//        when(objectMapper.convertValue(any())).thenReturn(moduloDTO);
//
//        moduloDTO = moduloService.desativar(idModulo);


//        assertNotNull();
//        assertEquals();
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
        Integer page = 0;
        Integer size = 6;
        ModuloEntity moduloEntity = getModuloEntity(1);
        List<ModuloEntity> list = new ArrayList<>();
        list.add(moduloEntity);
        when(moduloRepository.findAll()).thenReturn(list);

        PageDTO<ModuloDTO> list1 = moduloService.listarModulo(page, size);

        assertNotNull(list1);
    }

    @Test
    public void deveTestarListarAllModulo() {
        Integer page = 0;
        Integer size = 6;
        ModuloEntity moduloEntity = getModuloEntity(1);
        List<ModuloEntity> lista = new ArrayList<>();
        lista.add(moduloEntity);
        when(moduloRepository.findAll()).thenReturn(lista);

        PageDTO<ModuloDTO> list = moduloService.listarModulo(page, size);

        assertNotNull(list);
    }


    @Test
    public void deveTestarVincularModuloTrilha() throws RegraDeNegocioException {


        ModuloEntity moduloEntity = getModuloEntity(1);
        ModuloTrilhaDTO moduloTrilhaDTO = objectMapper.convertValue(moduloEntity, ModuloTrilhaDTO.class);
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
        // Criar variaveis (SETUP)
        List<ModuloEntity> moduloEntityList = new ArrayList<>();
        ModuloEntity moduloEntity = getModuloEntity(1);
        moduloEntityList.add(moduloEntity);
        when(moduloRepository.findAll()).thenReturn(moduloEntityList);
        // Ação (ACT)
        List<ModuloDTO> moduloDTOS = moduloService.listAllModulos();

        // Verificação (ASSERT)
        assertNotNull(moduloDTOS);
        assertEquals(1, moduloDTOS.size());
    }


    private ModuloEntity getModuloEntity(Integer idModulo) {
        ModuloEntity moduloEntity = new ModuloEntity();
        moduloEntity.setIdModulo(idModulo);
        moduloEntity.setAtivo(Ativo.S);
        moduloEntity.setNome("alok");
        moduloEntity.setDataInicio(LocalDate.of(2022, 10, 10));
        moduloEntity.setDataFim(LocalDate.of(2022, 10, 11));
        return moduloEntity;
    }

    private ModuloDTO getModuloDTO() {
        ModuloDTO moduloDTO = new ModuloDTO();
        moduloDTO.setIdModulo(1);
        moduloDTO.setNome("jonas");
        moduloDTO.setDataInicio(LocalDate.of(2022, 9, 2));
        moduloDTO.setDataFim(LocalDate.of(2022, 9, 10));
        return moduloDTO;
    }

    private ModuloCreateDTO getModuloCreateDTO() {
        ModuloCreateDTO moduloCreateDTO = new ModuloCreateDTO();
        moduloCreateDTO.setNome("jonas");
        moduloCreateDTO.setDataInicio(LocalDate.of(2022, 12, 2));
        moduloCreateDTO.setDataFim(LocalDate.of(2022, 12, 10));
        return moduloCreateDTO;
    }

    private static TrilhaEntity getTrilhaEntity(Integer idTrilha) {
        TrilhaEntity trilhaEntity = new TrilhaEntity();
        trilhaEntity.setIdTrilha(idTrilha);
        trilhaEntity.setNome("BACKEND");
        trilhaEntity.setAtivo(Ativo.S);
        return trilhaEntity;
    }

    private static TrilhaCreateDTO getTrilhaCreateDTO() {
        TrilhaCreateDTO trilhaCreateDTO = new TrilhaCreateDTO();
        trilhaCreateDTO.setNome("QA");
        trilhaCreateDTO.setDescricao("Trilha especializada em qualidade de software");
        return trilhaCreateDTO;
    }

    private static ModuloTrilhaDTO getModuloTrilhaDTO() {
        ModuloTrilhaDTO moduloTrilhaDTO = new ModuloTrilhaDTO();
        moduloTrilhaDTO.setIdModulo(1);
        moduloTrilhaDTO.setIdTrilha(1);
        return moduloTrilhaDTO;
    }
}

