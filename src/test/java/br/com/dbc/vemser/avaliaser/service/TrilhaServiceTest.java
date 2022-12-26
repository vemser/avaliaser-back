//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.vemrankser.ranqueamento.dto.*;
//import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
//import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
//import br.com.vemrankser.ranqueamento.enums.TipoPerfil;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.repository.TrilhaRepository;
//import br.com.vemrankser.ranqueamento.repository.UsuarioRepository;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TrilhaServiceTest {
//
//
//    @InjectMocks
//    private TrilhaService trilhaService;
//    @Mock
//    private TrilhaRepository trilhaRepository;
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private UsuarioService usuarioService;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(trilhaService, "objectMapper", objectMapper);
//    }
//
//    @Test
//    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
//
//        TrilhaCreateDTO trilhaCreateDTO = getTrilhaCreateDTO();
//        TrilhaEntity trilhaEntity = objectMapper.convertValue(trilhaCreateDTO, TrilhaEntity.class);
//
//
//        when(trilhaRepository.save(any())).thenReturn(trilhaEntity);
//
//        TrilhaDTO trilhaDTO1 = trilhaService.adicionar(trilhaCreateDTO);
//
//
//        assertNotNull(trilhaDTO1);
//
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarFindByIdTrilhaComSucesso() throws RegraDeNegocioException {
//
//        Integer idTrilha = 1;
//
//        when(trilhaRepository.findById(anyInt())).thenReturn(Optional.empty());
//
//        trilhaService.findById(idTrilha);
//    }
//
//    @Test
//    public void deveTestarFindAllEdicao() throws RegraDeNegocioException {
//
//        List<TrilhaEntity> listar = new ArrayList<>();
//        listar.add(getTrilhaEntity());
//
//        when(trilhaRepository.findAll()).thenReturn(listar);
//
//        trilhaService.findAllEdicao(null);
//        trilhaService.findAllEdicao(getTrilhaDTO().getEdicao());
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarTrilhaPorEdicao() throws RegraDeNegocioException {
//
//        TrilhaDTO trilhaDTO = getTrilhaDTO();
//
//        when(trilhaRepository.findByEdicao(any())).thenReturn(Optional.empty());
//
//        trilhaService.buscarTrilhaPorEdicao(trilhaDTO);
//
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarbuscarPorNomeTrilha() throws RegraDeNegocioException {
//
//        String nomeTrilha = "jonas";
//
//        when(trilhaRepository.findByNomeIgnoreCase(any())).thenReturn(Optional.empty());
//
//        trilhaService.buscarPorNomeTrilha(nomeTrilha);
//
//    }
//
//    @Test
//    public void deveTestarPegarIdTrilha() throws RegraDeNegocioException {
//
//
//        TrilhaEntity trilhaEntity = getTrilhaEntity();
//        TrilhaCreateDTO trilha = objectMapper.convertValue(trilhaEntity, TrilhaCreateDTO.class);
//
//        when(trilhaRepository.findById(any())).thenReturn(Optional.of(trilhaEntity));
//
//        TrilhaDTO trilhaDTO = trilhaService.pegarIdTrilha(trilha.getEdicao());
//
//        assertNotNull(trilhaDTO);
//
//    }
//
//    @Test
//    public void deveTestaraFindTrilhaByNome() {
//
//        List<TrilhaEntity> listar = new ArrayList<>();
//        listar.add(getTrilhaEntity());
//
//        String trilha = "alison";
//
//        when(trilhaRepository.findAll()).thenReturn(listar);
//        trilhaRepository.findAllByNomeContainingIgnoreCase(trilha);
//
//        trilhaService.findTrilhaByNome(null);
//        trilhaService.findTrilhaByNome(trilha);
//
//    }
//
//    @Test
//    public void deveTestarListarAlunosTrilha() {
//
//        Integer pagina = 5;
//        Integer quantidade = 3;
//        Integer trilha = 1;
//
//        TrilhaEntity trilhaEntity = getTrilhaEntity();
//        Page<TrilhaEntity> paginaMock = new PageImpl<>(List.of(trilhaEntity));
//        when(trilhaRepository.findAllByIdTrilha(any(), any())).thenReturn(paginaMock);
//
//        PageDTO<TrilhaPaginadoDTO> listarUsuarios = trilhaService.listarUsuariosNaTrilha(pagina, quantidade, trilha);
//
//        assertNotNull(listarUsuarios);
//        assertEquals(1, listarUsuarios.getQuantidadePaginas());
//        assertEquals(1, listarUsuarios.getTotalElementos());
//
//    }
//
//    @Test
//    public void deveTestarAdicionarIntrutorTrilha() throws RegraDeNegocioException {
//
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//        usuarioEntity.setTipoPerfil(3);
//        usuarioEntity.setStatusUsuario(1);
//        Mockito.spy(usuarioEntity);
//        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
//
//        String login = "paulo.pires";
//        TrilhaEntity trilhaEntity = new TrilhaEntity();
//        trilhaEntity.setIdTrilha(1);
//
//
//        when(usuarioService.pegarLoginInstrutor(anyString())).thenReturn(usuarioDTO);
//        when(trilhaRepository.save(any())).thenReturn(trilhaEntity);
//        when(trilhaRepository.findById(anyInt())).thenReturn(Optional.of(trilhaEntity));
//
//        trilhaService.adicionarIntrustorTrilha(login, List.of(1), null);
//
//        verify(trilhaRepository, times(1)).save(any());
//
//    }
//
//    @Test
//    public void deveTestarAdicionarAlunoTrilha() throws RegraDeNegocioException {
//
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//        usuarioEntity.setTipoPerfil(3);
//        usuarioEntity.setStatusUsuario(1);
//        Mockito.spy(usuarioEntity);
//        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
//
//        String login = "paulo.pires";
//        TrilhaEntity trilhaEntity = new TrilhaEntity();
//        trilhaEntity.setIdTrilha(1);
//
//
//        when(usuarioService.pegarLogin(anyString())).thenReturn(usuarioDTO);
//        when(trilhaRepository.save(any())).thenReturn(trilhaEntity);
//        when(trilhaRepository.findById(anyInt())).thenReturn(Optional.of(trilhaEntity));
//
//        trilhaService.adicionarAlunoTrilha(login, List.of(1), null);
//
//        verify(trilhaRepository, times(1)).save(any());
//
//    }
//
//
//    @Test
//    public void deveRetornar5UsuariosPelaPontuacaoDecrescenteRankingTrilha() throws RegraDeNegocioException {
//
//        TrilhaEntity trilha = getTrilhaEntity();
//        List<UsuarioEntity> usuarios = new ArrayList<>();
//        usuarios.add(getUsuarioEntity(10));
//        usuarios.add(getUsuarioEntity(15));
//        usuarios.add(getUsuarioEntity(20));
//        usuarios.add(getUsuarioEntity(30));
//        usuarios.add(getUsuarioEntity(25));
//
//        trilha.getUsuarios().addAll(usuarios);
//
//        when(trilhaRepository.findById(1)).thenReturn(Optional.of(trilha));
//
//
//        List<RankingDTO> expected = new ArrayList<>();
//        expected.add(getRankingDTO(30));
//        expected.add(getRankingDTO(25));
//        expected.add(getRankingDTO(20));
//        expected.add(getRankingDTO(15));
//        expected.add(getRankingDTO(10));
//        List<RankingDTO> result = trilhaService.rankingtrilha(1);
//
//
//        assertTrue(result.size() > 0);
//        assertEquals(expected, result);
//        assertEquals(expected.get(0).getNome(), result.get(0).getNome());
//        assertEquals(expected.get(0).getPontuacaoAluno(), result.get(0).getPontuacaoAluno());
//
//
//    }
//
//    @Test
//    public void deveTestarListarAllTrilhaPaginadoComSucesso() {
//        // SETUP
//        Integer pagina = 2;
//        Integer tamanho = 9;
//        TrilhaEntity trilhaEntity = getTrilhaEntity();
//        Page<TrilhaEntity> trilhaEntities = new PageImpl<>(List.of(trilhaEntity));
//
//        when(trilhaRepository.findAll(any(Pageable.class))).thenReturn(trilhaEntities);
//
//        // ACT
//        PageDTO<TrilhaDTO> trilhaDTOPageDTO = trilhaService.listarAllTrilhaPaginado(pagina, tamanho);
//        // ASSERT
//        assertNotNull(trilhaDTOPageDTO);
//    }
//
//
//    @Test
//    public void deveLancarExcecaoQuandoIdTrilhaForInvalido() {
//
//        when(trilhaRepository.findById(1)).thenReturn(Optional.empty());
//
//        try {
//            trilhaService.rankingtrilha(1);
//        } catch (Exception e) {
//            assertEquals("Trilha não encontrada.", e.getMessage());
//        }
//
//    }
//
//
//    private static TrilhaCreateDTO getTrilhaCreateDTO() {
//        TrilhaCreateDTO trilhaCreateDTO = new TrilhaCreateDTO();
//        trilhaCreateDTO.setNome("QA");
//        trilhaCreateDTO.setEdicao(11);
//        trilhaCreateDTO.setAnoEdicao(LocalDate.of(2023, 3, 10));
//        return trilhaCreateDTO;
//    }
//
//
//    private static TrilhaDTO getTrilhaDTO() {
//        TrilhaDTO trilhaDTO = new TrilhaDTO();
//        trilhaDTO.setIdTrilha(5);
//        trilhaDTO.setNome("FRONTEND");
//        trilhaDTO.setEdicao(12);
//        trilhaDTO.setAnoEdicao(LocalDate.of(2023, 3, 10));
//        return trilhaDTO;
//    }
//
//    private static TrilhaEntity getTrilhaEntity() {
//        TrilhaEntity trilhaEntity = new TrilhaEntity();
//        trilhaEntity.setIdTrilha(5);
//        trilhaEntity.setNome("BACKEND");
//        trilhaEntity.setAnoEdicao(LocalDate.of(2023, 3, 10));
//        trilhaEntity.setEdicao(13);
//        return trilhaEntity;
//    }
//
//    private static RankingDTO getRankingDTO(Integer pontuacao) {
//        RankingDTO rankingDTO = new RankingDTO();
//        rankingDTO.setNome("jonas");
//        rankingDTO.setPontuacaoAluno(pontuacao);
//        return rankingDTO;
//
//    }
//
//    private static UsuarioDTO getUsuarioDTO() {
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setIdUsuario(1);
//        usuarioDTO.setNome("Paulo pires");
//        usuarioDTO.setEmail("paulo@paulo");
//        usuarioDTO.setStatusUsuario(1);
//        usuarioDTO.setTipoPerfil(1);
//        usuarioDTO.setLogin("paulo.pires");
//        usuarioDTO.setCidade("sapiranga");
//        usuarioDTO.setEspecialidade("BACKEND");
//        return usuarioDTO;
//    }
//
//    public static UsuarioEntity getUsuarioEntity() {
//        return getUsuarioEntity(null);
//    }
//
//    public static UsuarioEntity getUsuarioEntity(Integer pontuacao) {
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//        usuarioEntity.setIdUsuario(1);
//        usuarioEntity.setFoto(null);
//        usuarioEntity.setStatusUsuario(1);
//        if (pontuacao == null) {
//            usuarioEntity.setPontuacaoAluno(10);
//        } else {
//            usuarioEntity.setPontuacaoAluno(pontuacao);
//        }
//        usuarioEntity.setLogin("jonas.lara");
//        usuarioEntity.setNome("jonas");
//        usuarioEntity.setSenha("123");
//        usuarioEntity.setCidade("sapiranga");
//        usuarioEntity.setEspecialidade("backend");
//        usuarioEntity.setEmail("jonas@hotmail.com");
//        usuarioEntity.setCargos(new HashSet<>());
//        usuarioEntity.setTipoPerfil(TipoPerfil.ALUNO.getCargo());
//        return usuarioEntity;
//    }
//
//    public static LoginTrilhaDTO getLoginTrilhaDTO() {
//        LoginTrilhaDTO loginTrilhaDTO = new LoginTrilhaDTO();
//        loginTrilhaDTO.setIdTrilha(loginTrilhaDTO.getIdTrilha());
//        loginTrilhaDTO.setLogin("jonas.lara");
//        return loginTrilhaDTO;
//    }
//
//    public static LoginDTO getLoginDTO() {
//        LoginDTO loginDTO = new LoginDTO();
//        loginDTO.setSenha("123");
//        loginDTO.setEmail("jonas@jonas");
//        return loginDTO;
//    }
//}
