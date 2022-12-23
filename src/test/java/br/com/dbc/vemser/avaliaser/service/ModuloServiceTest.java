package br.com.dbc.vemser.avaliaser.service;

import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
import br.com.vemrankser.ranqueamento.dto.ModuloTrilhaDTO;
import br.com.vemrankser.ranqueamento.dto.TrilhaCreateDTO;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.enums.StatusModulo;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ModuloRepository;
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
import java.time.LocalDateTime;
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
    public void deveTestarAdicionarComSucesso() {

        ModuloCreateDTO moduloCreateDTO = getModuloCreateDTO();

        when(moduloRepository.save(any())).thenReturn(getModuloEntity(1));

        ModuloDTO moduloDTO1 = moduloService.adicionar(moduloCreateDTO);


        assertNotNull(moduloDTO1);

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
        List<ModuloEntity> list = new ArrayList<>();
        list.add(moduloEntity);
        when(moduloRepository.findAll()).thenReturn(list);

        List<ModuloDTO> list1 = moduloService.listarModulo();

        assertNotNull(list1);
    }

    @Test
    public void deveTestarListarAllModulo() {
        ModuloEntity moduloEntity = getModuloEntity(1);
        List<ModuloEntity> lista = new ArrayList<>();
        lista.add(moduloEntity);
        when(moduloRepository.findAll()).thenReturn(lista);

        List<ModuloDTO> list = moduloService.listarModulo();

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

        ModuloDTO moduloDTO = moduloService.vincularModuloTrilha(moduloEntity.getIdModulo(), trilhaEntity.getIdTrilha(), moduloTrilhaDTO);


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
        moduloEntity.setStatusModulo(StatusModulo.S);
        moduloEntity.setNome("alok");
        moduloEntity.setDataInicio(LocalDateTime.of(2022, 10, 10, 21, 21));
        moduloEntity.setDataFim(LocalDateTime.of(2022, 10, 11, 21, 21));
        return moduloEntity;
    }

    private ModuloDTO getModuloDTO() {
        ModuloDTO moduloDTO = new ModuloDTO();
        moduloDTO.setIdModulo(1);
        moduloDTO.setNome("jonas");
        moduloDTO.setDataInicio(LocalDateTime.of(2022, 9, 2, 17, 21));
        moduloDTO.setDataFim(LocalDateTime.of(2022, 9, 10, 21, 21));
        return moduloDTO;
    }

    private ModuloCreateDTO getModuloCreateDTO() {
        ModuloCreateDTO moduloCreateDTO = new ModuloCreateDTO();
        moduloCreateDTO.setNome("jonas");
        moduloCreateDTO.setDataInicio(LocalDateTime.of(2022, 12, 2, 14, 10));
        moduloCreateDTO.setDataFim(LocalDateTime.of(2022, 12, 10, 14, 10));
        return moduloCreateDTO;
    }

    private static TrilhaEntity getTrilhaEntity(Integer idTrilha) {
        TrilhaEntity trilhaEntity = new TrilhaEntity();
        trilhaEntity.setIdTrilha(idTrilha);
        trilhaEntity.setNome("BACKEND");
        trilhaEntity.setAnoEdicao(LocalDate.of(2023, 3, 10));
        trilhaEntity.setEdicao(13);
        return trilhaEntity;
    }

    private static TrilhaCreateDTO getTrilhaCreateDTO() {
        TrilhaCreateDTO trilhaCreateDTO = new TrilhaCreateDTO();
        trilhaCreateDTO.setNome("QA");
        trilhaCreateDTO.setEdicao(11);
        trilhaCreateDTO.setAnoEdicao(LocalDate.of(2023, 3, 10));
        return trilhaCreateDTO;
    }

    private static ModuloTrilhaDTO getModuloTrilhaDTO() {
        ModuloTrilhaDTO moduloTrilhaDTO = new ModuloTrilhaDTO();
        moduloTrilhaDTO.setIdModulo(1);
        moduloTrilhaDTO.setIdTrilha(1);
        return moduloTrilhaDTO;
    }
}

