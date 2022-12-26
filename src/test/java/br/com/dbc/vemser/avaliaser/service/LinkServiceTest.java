//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.vemrankser.ranqueamento.dto.LinkDTO;
//import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
//import br.com.vemrankser.ranqueamento.entity.LinkEntity;
//import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.repository.LinkRepository;
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
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LinkServiceTest {
//    @InjectMocks
//    private LinkService linkService;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Mock
//    private LinkRepository linkRepository;
//    @Mock
//    private UsuarioService usuarioService;
//    @Mock
//    private AtividadeService atividadeService;
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(linkService, "objectMapper", objectMapper);
//    }
//
//    @Test
//    public void deveTestarCreateLinkComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        LinkEntity linkEntity = new LinkEntity();
//        AtividadeEntity atividadeEntity = new AtividadeEntity();
//        atividadeEntity.setIdAtividade(1);
//        UsuarioEntity usuario = new UsuarioEntity();
//        usuario.setIdUsuario(1);
//        LinkDTO linkDTO = objectMapper.convertValue(linkEntity, LinkDTO.class);
//
//        when(atividadeService.buscarPorIdAtividade(anyInt())).thenReturn(atividadeEntity);
//        when(usuarioService.findById(anyInt())).thenReturn(usuario);
//        when(linkRepository.save(any())).thenReturn(linkEntity);
//
//        // Ação (ACT)
//        LinkDTO link = linkService.create(atividadeEntity.getIdAtividade(), usuario.getIdUsuario(), linkDTO);
//
//        // Verificação (ASSERT)
//        assertNotNull(link);
//
//    }
//}
