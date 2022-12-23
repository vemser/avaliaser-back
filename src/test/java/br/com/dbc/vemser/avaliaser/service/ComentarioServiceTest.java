package br.com.dbc.vemser.avaliaser.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.enums.TipoFeedback;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import br.com.vemrankser.ranqueamento.repository.ComentarioRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTest {

    @InjectMocks
    private ComentarioService comentarioService;

    @Mock
    private AtividadeService atividadeService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private AtividadeRepository atividadeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(comentarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarComentarioAvaliarComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer idAluno = 44;
        Integer idAtividade = 33;
        AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO = getAtividadeComentarioAvaliacaoCreateDTO();
        AtividadeEntity atividadeEntity = getAtividadeEntity();
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        ComentarioEntity comentarioEntity = getComentarioEntity();

        when(atividadeService.buscarPorIdAtividade(anyInt())).thenReturn(atividadeEntity);
        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);
        when(comentarioRepository.save(any())).thenReturn(comentarioEntity);
//        when(atividadeRepository.save(any())).thenReturn(atividadeEntity);
        // ACT
        AtividadeComentarioAvaliacaoDTO atividadeComentarioAvaliacaoDTO = comentarioService.adicionarComentarioAvaliar(atividadeComentarioAvaliacaoCreateDTO, idAluno, idAtividade);
        // ASSERT
        assertNotNull(atividadeComentarioAvaliacaoDTO);
    }

    @Test
    public void deveTestarAdicionarFeedbackComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer idAluno = 22;
        TipoFeedback tipoFeedback = TipoFeedback.POSITIVO;
        ComentarioEntity comentarioEntity = getComentarioEntity();
        ComentarioDTO comentarioDTO = getComentarioDTO();
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioService.findById(anyInt())).thenReturn(usuarioEntity);
        when(comentarioRepository.save(any())).thenReturn(comentarioEntity);

        // ACT
        ComentarioCreateDTO comentarioCreateDTO1 = comentarioService.adicionarFeedback(comentarioDTO, idAluno, tipoFeedback);

        // ASSERT
        assertNotNull(comentarioCreateDTO1);
        assertEquals("Pontos de melhoria...", comentarioCreateDTO1.getComentario());
    }

    @Test
    public void deveTestarComentarioDoAlunoComSucesso() {
        // SETUP
        Integer pagina = 2;
        Integer tamanho = 9;
        Integer idAluno = 38;
        ComentarioDTO comentarioDTO = getComentarioDTO();
        ComentarioEntity comentarioEntity = getComentarioEntity();
        Page<ComentarioEntity> comentarioEntityPageDTO = new PageImpl<>(List.of(comentarioEntity));

        when(comentarioRepository.findAllByIdUsuario(any(Pageable.class), anyInt())).thenReturn(comentarioEntityPageDTO);

        // ACT
        PageDTO<ComentarioDTO> comentarioPageServiceDTO = comentarioService.comentariosDoAluno(pagina, tamanho, idAluno);
        // ASSERT
        assertNotNull(comentarioPageServiceDTO);
    }

    @Test
    public void deveTestarlistarComentarioPorAtividadeComSucesso() throws RegraDeNegocioException {

        // SETUP
        Integer id = 3;
        AtividadeEntity atividadeEntity = getAtividadeEntity();
        atividadeEntity.setIdAtividade(12);

        ComentarioDTO comentarioDTO = getComentarioDTO();
        comentarioDTO.setComentario("Muito bom!");

        when(atividadeService.buscarPorIdAtividade(anyInt())).thenReturn(atividadeEntity);
        when(comentarioRepository.findAllByIdAtividade(anyInt())).thenReturn(List.of(getComentarioEntity()));
//        when(objectMapper.convertValue())

        // ACT
        List<ComentarioDTO> comentarioDTOList = comentarioService.listarComentarioPorAtividade(id);

        // ASSERT
        assertNotNull(comentarioDTO);
        assertEquals(1, comentarioDTOList.size());
    }

    @Test
    public void DeveTestarListarComentarioPorFeedback() {
        // SETUP
        TipoFeedback tipoFeedback = TipoFeedback.POSITIVO;
        ComentarioEntity comentarioEntity = getComentarioEntity();
        List<ComentarioEntity> comentarioPageDTO = List.of(comentarioEntity);

        when(comentarioRepository.findAllByStatusComentario(anyInt())).thenReturn(comentarioPageDTO);

        // ACT
        List<ComentarioDTO> comentarioDTO = comentarioService.listarComentarioPorFeedback(tipoFeedback);
        // ASSERT
        assertNotNull(comentarioDTO);
        assertEquals(true, comentarioDTO.size() > 0);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarBuscarComentarioPorIdComFalha() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 4;

        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        // Ação (ACT)
        ComentarioEntity comentarioEntity = comentarioService.buscarPorIdComentario(idComentario);

        // Verificação (ASSERT)

    }

    @Test
    public void deveTestarBuscarComentarioPorIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 4;

        ComentarioEntity comentario = getComentarioEntity();
        comentario.setIdComentario(idComentario);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentario));
        // Ação (ACT)
        ComentarioEntity comentarioEntity = comentarioService.buscarPorIdComentario(idComentario);

        // Verificação (ASSERT)
        assertEquals(4, comentarioEntity.getIdComentario());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 4;

        ComentarioEntity comentarioEntity = getComentarioEntity();
        comentarioEntity.setIdComentario(idComentario);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentarioEntity));
        // Ação (ACT)
        comentarioService.delete(idComentario);

        // Verificação (ASSERT)
        verify(comentarioRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 11;
        ComentarioEntity comentarioEntity = getComentarioEntity();
        comentarioEntity.setIdComentario(idComentario);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentarioEntity));

        // Ação (ACT)
        ComentarioEntity comentario = comentarioService.findById(idComentario);

        // Verificação (ASSERT)
        assertNotNull(comentarioEntity);
        assertEquals(11, comentarioEntity.getIdComentario());

    }

    @Test
    public void deveTestarCalcularPontuacaoComSucesso() {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setPontuacaoAluno(10);
        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setPontuacao(10);


        // Ação (ACT)
        Integer integer = comentarioService.calcularPontuacao(usuarioEntity, atividadeEntity);

        // Verificação (ASSERT)
        assertNotNull(integer);
        assertEquals(20, integer);

    }

    public static ComentarioEntity getComentarioEntity() {
        ComentarioEntity comentarioEntity = new ComentarioEntity();

        comentarioEntity.setIdComentario(11);
        comentarioEntity.setIdAtividade(44);
        comentarioEntity.setComentario("Pontos de melhoria...");

        return comentarioEntity;
    }

    public static ComentarioAvaliacaoDTO getComentarioAvaliacaoDTO() {
        ComentarioAvaliacaoDTO comentarioAvaliacaoDTO = new ComentarioAvaliacaoDTO();
        comentarioAvaliacaoDTO.setComentario("Parabéns . . .");

        return comentarioAvaliacaoDTO;
    }

    public static ComentarioDTO getComentarioDTO() {
        ComentarioDTO comentarioDTO = new ComentarioDTO();

        comentarioDTO.setComentario("Pontos de melhoria...");

        return comentarioDTO;
    }

    public static ComentarioCreateDTO getComentarioCreateDTO() {
        ComentarioDTO comentarioDTO = new ComentarioDTO();

        comentarioDTO.setComentario("Pontos de melhoria...");

        return comentarioDTO;
    }

    public static AtividadeEntity getAtividadeEntity() {
        AtividadeEntity atividadeEntity = new AtividadeEntity();

        atividadeEntity.setIdAtividade(1);
        atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE);
        atividadeEntity.setPesoAtividade(3);

        return atividadeEntity;
    }

    public static AtividadeAvaliarDTO getAtividadeAvaliarDTO() {
        AtividadeAvaliarDTO atividadeAvaliarDTO = new AtividadeAvaliarDTO();

        atividadeAvaliarDTO.setPontuacao(90);
        //  atividadeAvaliarDTO.setLink("www.githu.com");

        return atividadeAvaliarDTO;
    }

    public static AtividadeAvaliacaoDTO getAtividadeAvaliacaoDTO() {
        AtividadeAvaliacaoDTO atividadeAvaliacaoDTO = new AtividadeAvaliacaoDTO();
        atividadeAvaliacaoDTO.setPontuacao(100);

        return atividadeAvaliacaoDTO;
    }

    public static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setIdUsuario(22);
        usuarioEntity.setNome("Robert Plant");
        usuarioEntity.setPontuacaoAluno(100);

        return usuarioEntity;
    }

    public static AtividadeComentarioAvaliacaoDTO getAtividadeComentarioAvaliacaoDTO() {
        AtividadeComentarioAvaliacaoDTO atividadeComentarioAvaliacaoDTO = new AtividadeComentarioAvaliacaoDTO();
        atividadeComentarioAvaliacaoDTO.setComentario("Pontos de melhoria...");
        atividadeComentarioAvaliacaoDTO.setNotaAvalicao(100);

        return atividadeComentarioAvaliacaoDTO;
    }

    public static AtividadeComentarioAvaliacaoCreateDTO getAtividadeComentarioAvaliacaoCreateDTO() {
        AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO = new AtividadeComentarioAvaliacaoCreateDTO();
        atividadeComentarioAvaliacaoCreateDTO.setComentario("Pontos de melhoria...");
        atividadeComentarioAvaliacaoCreateDTO.setNotaAvalicao(100);

        return atividadeComentarioAvaliacaoCreateDTO;
    }
}
