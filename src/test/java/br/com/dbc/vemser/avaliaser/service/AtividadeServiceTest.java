//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.login.UsuarioLogadoDTO;
//import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto.AtividadeCreateDTO;
//import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto.AtividadeDTO;
//import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadepagedto.AtividadePaginacaoDTO;
//import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
//import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
//import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.repositories.vemrankser.AtividadeRepository;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.UsuarioService;
//import br.com.dbc.vemser.avaliaser.services.vemrankser.AtividadeService;
//import br.com.dbc.vemser.avaliaser.services.vemrankser.ModuloService;
//import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
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
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.util.ReflectionTestUtils;
//
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
//public class AtividadeServiceTest {
//
//    @InjectMocks
//    private AtividadeService atividadeService;
//
//    @Mock
//    private UsuarioService usuarioService;
//
//    @Mock
//    private ModuloService moduloService;
//
//    @Mock
//    private TrilhaService trilhaService;
//
//    @Mock
//    private AtividadeRepository atividadeRepository;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(atividadeService, "objectMapper", objectMapper);
//    }
//
//    @Test
//    public void DeveTestarListarAtividadesComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        Integer pagina = 1;
//        Integer tamanho = 9;
//        AtividadeEntity atividadeEntity = getAtividadeEntity();
//        AtividadePaginacaoDTO atividadePaginacaoDTO = getAtividadePaginacaoDTO();
//        AtividadeDTO atividadeDTO = getAtividadeDTO();
//        Page<AtividadeEntity> atividadePageDTO = new PageImpl<>(List.of(atividadeEntity));
//
//        when(atividadeRepository.findAll(any(Pageable.class))).thenReturn(atividadePageDTO);
//
//        // Ação (ACT)
//        AtividadePaginacaoDTO atividadeDTO1 = atividadeService.listarAtividades(pagina, tamanho);
//        // Verificação (ASSERT)
//        assertNotNull(atividadeDTO1);
//        assertEquals(1, atividadeDTO1.getPagina());
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void DeveTestarListarAtividadesComErro() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        Integer pagina = 1;
//        Integer tamanho = 9;
//        AtividadeEntity atividadeEntity = getAtividadeEntity();
//        AtividadePaginacaoDTO atividadePaginacaoDTO = getAtividadePaginacaoDTO();
//        AtividadeDTO atividadeDTO = getAtividadeDTO();
//        Page<AtividadeEntity> atividadePageDTO = new PageImpl<>(List.of(atividadeEntity));
//
//        when(atividadeRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
//
//        // Ação (ACT)
//        AtividadePaginacaoDTO atividadeDTO1 = atividadeService.listarAtividades(pagina, tamanho);
//    }
//
//    @Test
//    public void deveTestarCreateAtividadeComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        Integer idModulo = 22;
//        UsuarioLogadoDTO usuarioLogadoDTO = getUsuarioLogadoDTO();
//        AtividadeCreateDTO atividadeCreateDTO = getAtividadeCreateDTO();
//        ModuloEntity moduloEntity = getModuloEntity();
//        TrilhaEntity trilhaEntity = getTrilhaEntity();
//
//        when(usuarioService.getLoggedUser()).thenReturn(usuarioLogadoDTO);
//        when(moduloService.buscarPorIdModulo(anyInt())).thenReturn(moduloEntity);
//        when(trilhaService.findById(anyInt())).thenReturn(trilhaEntity);
//
//        // Ação (ACT)
//        AtividadeDTO atividadeDTOCreate = atividadeService.createAtividade(atividadeCreateDTO, idModulo, List.of(1, 2, 3));
//        // Verificação (ASSERT)
//        assertNotNull(atividadeDTOCreate);
//        assertEquals("Spring Data", atividadeDTOCreate.getTitulo());
//    }
//
//    @Test
////    public void deveTestarColocarAtividadeComoConcluidaComSucesso() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer idAtividade = 11;
////        AtividadeEntity atividadeEntity = getAtividadeEntity();
////        AtividadeDTO atividadeDTO = getAtividadeDTO();
////
////        when(atividadeRepository.findById(anyInt())).thenReturn(Optional.of(atividadeEntity));
////        when(atividadeRepository.save(any())).thenReturn(atividadeEntity);
////
////        // Ação (ACT)
////        AtividadeDTO atividadeDTO1 = atividadeService.colocarAtividadeComoConcluida(atividadeDTO.getIdAtividade());
////
////        // Verificação (ASSERT)
////        assertNotNull(atividadeDTO1);
////    }
//
////    @Test(expected = RegraDeNegocioException.class)
////    public void deveTestarListarAtividadePorStatusComErro() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer pagina = 0;
////        Integer tamanho = 6;
////        Integer idTrilha = 8;
////        AtividadeStatus atividadeStatus = AtividadeStatus.CONCLUIDA;
////        AtividadeTrilhaDTO atividadeTrilhaDTO = getAtividadeTrilhaDTO();
////        Page<AtividadeTrilhaDTO> atividadeTrilhaDTOS = new PageImpl<>(List.of(atividadeTrilhaDTO));
////
////        when(atividadeRepository.listarAtividadePorStatus(any(Pageable.class), anyInt(), any())).thenReturn(Page.empty());
////
////        // Ação (ACT)
////        atividadeService.listarAtividadePorStatus(pagina, tamanho, idTrilha, atividadeStatus);
////    }
//
////    @Test
////    public void deveTestarListarAtividadePorStatusComSucesso() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer pagina = 0;
////        Integer tamanho = 6;
////        Integer idTrilha = 8;
////        AtividadeStatus atividadeStatus = AtividadeStatus.CONCLUIDA;
////        AtividadeTrilhaDTO atividadeTrilhaDTO = getAtividadeTrilhaDTO();
////        Page<AtividadeTrilhaDTO> atividadeTrilhaDTOS = new PageImpl<>(List.of(atividadeTrilhaDTO));
////
////        when(atividadeRepository.listarAtividadePorStatus(any(Pageable.class), anyInt(), any())).thenReturn(atividadeTrilhaDTOS);
////
////        // Ação (ACT)
////        PageDTO listarPorStatus = atividadeService.listarAtividadePorStatus(pagina, tamanho, idTrilha, atividadeStatus);
////        // Verificação (ASSERT)
////        assertNotNull(listarPorStatus);
////        assertEquals(1, listarPorStatus.getTotalElementos());
////    }
////
////    @Test(expected = RegraDeNegocioException.class)
////    public void deveTestarListarAtividadeMuralInstrutorComErro() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer pagina = 0;
////        Integer tamanho = 6;
////        Integer idTrilha = 8;
////        AtividadeMuralDTO atividadeMuralDTO = getAtividadeMuralDTO();
////        Page<AtividadeMuralDTO> atividadeMuralInstrutorDTO = new PageImpl<>(List.of(atividadeMuralDTO));
////
////        when(atividadeRepository.listarAtividadeMuralInstrutor(any(Pageable.class), anyInt())).thenReturn(Page.empty());
////
////        // Ação (ACT)
////        atividadeService.listarAtividadeMuralInstrutor(pagina, tamanho, idTrilha);
////    }
////
////    @Test
////    public void deveTestarListarAtividadeMuralInstrutorComSucesso() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer pagina = 0;
////        Integer tamanho = 6;
////        Integer idTrilha = 8;
////        AtividadeMuralDTO atividadeMuralDTO = getAtividadeMuralDTO();
////        Page<AtividadeMuralDTO> atividadeMuralInstrutorDTO = new PageImpl<>(List.of(atividadeMuralDTO));
////
////        when(atividadeRepository.listarAtividadeMuralInstrutor(any(Pageable.class), anyInt())).thenReturn(atividadeMuralInstrutorDTO);
////
////        // Ação (ACT)
////        PageDTO listarAtividadeMuralInstrutor = atividadeService.listarAtividadeMuralInstrutor(pagina, tamanho, idTrilha);
////        // Verificação (ASSERT)
////        assertNotNull(listarAtividadeMuralInstrutor);
////        assertEquals(1, listarAtividadeMuralInstrutor.getTotalElementos());
////    }
////
////    @Test
////    public void deveTestarListarAtividadeMuralAlunoComSucesso() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer pagina = 0;
////        Integer tamanho = 6;
////        Integer idAluno = 8;
////        AtividadeStatus atividadeStatus = AtividadeStatus.CONCLUIDA;
////        AtividadeMuralAlunoDTO atividadeMuralAlunoDTO = getAtividadeMuralAlunoDTO();
////        Page<AtividadeMuralAlunoDTO> atividadeMuralAlunoPage = new PageImpl<>(List.of(atividadeMuralAlunoDTO));
////
////        when(atividadeRepository.listarAtividadeMuralAluno(anyInt(), any(), any(Pageable.class))).thenReturn(atividadeMuralAlunoPage);
////
////        // Ação (ACT)
////        PageDTO listarAtividadeMuralAluno = atividadeService.listarAtividadeMuralAluno(pagina, tamanho, idAluno, atividadeStatus);
////        // Verificação (ASSERT)
////        assertNotNull(listarAtividadeMuralAluno);
////        assertEquals(1, listarAtividadeMuralAluno.getTotalElementos());
////    }
////
////    @Test
////    public void deveTestarListarAtividadePorIdTrilhaIdModuloComSucesso() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer idTrilha = 1;
////        Integer idModulo = 2;
////        Integer pagina = 0;
////        Integer tamanho = 6;
////        AtividadeNotaPageDTO atividadeNotaPageDTO = getAtividadeNotaPageDTO();
////        Page<AtividadeNotaPageDTO> pageDTO = new PageImpl<>(List.of(atividadeNotaPageDTO));
////        when(atividadeRepository.listarAtividadePorIdTrilhaIdModulo(any(Pageable.class), anyInt(), anyInt(), any())).thenReturn(pageDTO);
////
////        // Ação (ACT)
////        PageDTO pageDTO1 = atividadeService.listarAtividadePorIdTrilhaIdModulo(pagina, tamanho, idTrilha, idModulo, AtividadeStatus.CONCLUIDA);
////
////        // Verificação (ASSERT)
////        assertNotNull(pageDTO1);
////        assertEquals(1, pageDTO1.getTotalElementos());
////
////    }
////
////    @Test(expected = RegraDeNegocioException.class)
////    public void deveTestarBuscarPorIdAtividadeComErro() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer idAtividade = 11;
////        AtividadeEntity atividadeEntity = getAtividadeEntity();
////        atividadeEntity.setIdAtividade(idAtividade);
////        when(atividadeRepository.findById(anyInt())).thenReturn(Optional.empty());
////
////        // Ação (ACT)
////        AtividadeDTO atividadeDTO = atividadeService.findById(idAtividade);
////
////    }
////
////    @Test
////    public void deveTestarBuscarPorIdAtividadeComSucesso() throws RegraDeNegocioException {
////        // Criar variaveis (SETUP)
////        Integer idAtividade = 11;
////        AtividadeEntity atividadeEntity = getAtividadeEntity();
////        atividadeEntity.setIdAtividade(idAtividade);
////        when(atividadeRepository.findById(anyInt())).thenReturn(Optional.of(atividadeEntity));
////
////        // Ação (ACT)
////        AtividadeDTO atividadeDTO = atividadeService.findById(idAtividade);
////
////        // Verificação (ASSERT)
////        assertNotNull(atividadeDTO);
////        assertEquals(11, atividadeDTO.getIdAtividade());
////
////    }
////
////    @Test
////    public void deveTestarSaveAtividadeComSucesso() throws RegraDeNegocioException {
////
////        // SETUP
////        Integer id = 3;
////        AtividadeDTO atividadeDTO = getAtividadeDTO();
////        atividadeDTO.setIdAtividade(id);
////
////        AtividadeEntity atividadeEntity = getAtividadeEntity();
////
////        when(atividadeRepository.save(any())).thenReturn(atividadeEntity);
////
////        // ACT
////        atividadeService.save(atividadeEntity);
////
////        // ASSERT
////        assertNotNull(atividadeEntity);
//////        assertEquals(1, comentarioDTOList.size());
////    }
////
////
////    public static AtividadeDTO getAtividadeDTO() {
////        AtividadeDTO atividadeDTO = new AtividadeDTO();
////
////        atividadeDTO.setIdAtividade(11);
////        atividadeDTO.setPesoAtividade(2);
////        atividadeDTO.setTitulo("Java OO");
////        atividadeDTO.setNomeInstrutor("Robert Plant");
////
////        return atividadeDTO;
////    }
////
////    public static AtividadeCreateDTO getAtividadeCreateDTO() {
////        AtividadeCreateDTO atividadeCreateDTO = new AtividadeCreateDTO();
////        atividadeCreateDTO.setIdModulo(3);
////        atividadeCreateDTO.setTitulo("Spring Data");
////        atividadeCreateDTO.setPesoAtividade(2);
////
////        return atividadeCreateDTO;
////    }
////
////    public static AtividadeEntity getAtividadeEntity() {
////        AtividadeEntity atividadeEntity = new AtividadeEntity();
////
////        atividadeEntity.setIdAtividade(11);
////        atividadeEntity.setPesoAtividade(2);
////        atividadeEntity.setTitulo("Java OO");
////        atividadeEntity.setNomeInstrutor("Robert Plant");
////
////        return atividadeEntity;
////    }
////
////    public static AtividadeNotaDTO getAtividadeNotaDTO() {
////        AtividadeNotaDTO atividadeNotaDTO = new AtividadeNotaDTO();
////
////        atividadeNotaDTO.setIdAtividade(11);
////        atividadeNotaDTO.setNota(100);
////        atividadeNotaDTO.setNome("Java OO");
////
////        return atividadeNotaDTO;
////    }
////
////    public static AtividadeNotaPageDTO getAtividadeNotaPageDTO() {
////        AtividadeNotaPageDTO atividadeNotaPageDTO = new AtividadeNotaPageDTO();
////
////        atividadeNotaPageDTO.setIdAtividade(11);
////        atividadeNotaPageDTO.setNota(100);
////        atividadeNotaPageDTO.setNome("Java OO");
////
////        return atividadeNotaPageDTO;
////    }
////
////    public static AtividadeMuralAlunoDTO getAtividadeMuralAlunoDTO() {
////        AtividadeMuralAlunoDTO atividadeMuralAlunoDTO = new AtividadeMuralAlunoDTO();
////        atividadeMuralAlunoDTO.setIdAtividade(11);
////        atividadeMuralAlunoDTO.setPesoAtividade(2);
////        atividadeMuralAlunoDTO.setTitulo("Java OO");
////        atividadeMuralAlunoDTO.setStatusAtividade(AtividadeStatus.CONCLUIDA);
////
////        return atividadeMuralAlunoDTO;
////    }
////
////    public static AtividadeMuralDTO getAtividadeMuralDTO() {
////        AtividadeMuralDTO atividadeMuralDTO = new AtividadeMuralDTO();
////        atividadeMuralDTO.setIdAtividade(11);
////        atividadeMuralDTO.setPesoAtividade(2);
////        atividadeMuralDTO.setTitulo("Java OO");
////        atividadeMuralDTO.setStatusAtividade(AtividadeStatus.CONCLUIDA);
////
////        return atividadeMuralDTO;
////    }
////
////    public static AtividadeTrilhaDTO getAtividadeTrilhaDTO() {
////        AtividadeTrilhaDTO atividadeTrilhaDTO = new AtividadeTrilhaDTO();
////        atividadeTrilhaDTO.setIdAtividade(11);
////        atividadeTrilhaDTO.setPesoAtividade(2);
////        atividadeTrilhaDTO.setTitulo("Java OO");
////        atividadeTrilhaDTO.setNomeInstrutor("Robert Plant");
////
////        return atividadeTrilhaDTO;
////    }
////
////    public static AtividadePaginacaoDTO getAtividadePaginacaoDTO() {
////        AtividadePaginacaoDTO atividadePaginacaoDTO = new AtividadePaginacaoDTO();
////        atividadePaginacaoDTO.setElementos(List.of(1, 2, 3, 4, 5));
////        atividadePaginacaoDTO.setPagina(0);
////        atividadePaginacaoDTO.setQuantidadePaginas(3);
////        atividadePaginacaoDTO.setTamanho(5);
////        atividadePaginacaoDTO.setTotalElementos(10L);
////
////        return atividadePaginacaoDTO;
////    }
////
////    public static UsuarioLogadoDTO getUsuarioLogadoDTO() {
////        UsuarioLogadoDTO usuarioLogadoDTO = new UsuarioLogadoDTO();
////        usuarioLogadoDTO.setIdUsuario(11);
////        usuarioLogadoDTO.setNome("Robert Plant");
////        usuarioLogadoDTO.setTipoPerfil(2);
////
////        return usuarioLogadoDTO;
////    }
////
////    public static ModuloEntity getModuloEntity() {
////        ModuloEntity moduloEntity = new ModuloEntity();
////        moduloEntity.setIdModulo(22);
////        moduloEntity.setNome("Spring Data");
////        moduloEntity.setStatusModulo(StatusModulo.S);
////
////        return moduloEntity;
////    }
////
////    public static TrilhaEntity getTrilhaEntity() {
////        TrilhaEntity trilhaEntity = new TrilhaEntity();
////        trilhaEntity.setIdTrilha(1);
////        trilhaEntity.setNome("Backend");
////
////        return trilhaEntity;
////    }
//
//
//}
