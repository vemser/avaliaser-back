package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;
    @Mock
    private AlunoRepository alunoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(alunoService, "objectMapper", objectMapper);
    }

    @Test
    public void DeveListarUsuarioPaginadoBuscandoPorIdCorretamente() throws RegraDeNegocioException {
        final int numeroPagina = 0;
        final int tamanho = 3;
        final int idAluno = 1;
        final String nome = null;
        final String email = null;

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        PageImpl<AlunoEntity> listaPaginada = new PageImpl<>(List.of(aluno), PageRequest.of(numeroPagina, tamanho), 0);
        when(alunoRepository.findAllByIdAlunoAndAtivo(any(), any(), any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarAlunoPaginado(idAluno,nome, email, numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(1, alunoDTOPageDTO.getTotalElementos());
        assertEquals(1, alunoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), alunoDTOPageDTO.getPagina());
    }

    @Test
    public void DeveListarUsuarioPaginadoBuscandoPorNomeCorretamente() throws RegraDeNegocioException {
        final int numeroPagina = 0;
        final int tamanho = 3;
        final Integer idAluno = null;
        final String nome = "Paulo";
        final String email = null;

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        PageImpl<AlunoEntity> listaPaginada = new PageImpl<>(List.of(aluno), PageRequest.of(numeroPagina, tamanho), 0);
        when(alunoRepository.findAllByNomeContainingIgnoreCaseAndAtivo(any(), any(), any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarAlunoPaginado(idAluno,nome, email, numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(1, alunoDTOPageDTO.getTotalElementos());
        assertEquals(1, alunoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), alunoDTOPageDTO.getPagina());
    }

    @Test
    public void DeveListarUsuarioPaginadoBuscandoPorEmailCorretamente() throws RegraDeNegocioException {
        final int numeroPagina = 0;
        final int tamanho = 3;
        final Integer idAluno = null;
        final String nome = null;
        final String email = "paulo.sergio";

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        PageImpl<AlunoEntity> listaPaginada = new PageImpl<>(List.of(aluno), PageRequest.of(numeroPagina, tamanho), 0);
        when(alunoRepository.findAllByEmailContainingIgnoreCaseAndAtivo(any(), any(), any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarAlunoPaginado(idAluno,nome, email, numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(1, alunoDTOPageDTO.getTotalElementos());
        assertEquals(1, alunoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), alunoDTOPageDTO.getPagina());
    }

    @Test
    public void DeveListarUsuarioPaginadoBuscandoCorretamente() throws RegraDeNegocioException {
        final int numeroPagina = 0;
        final int tamanho = 3;
        final Integer idAluno = null;
        final String nome = null;
        final String email = null;

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        PageImpl<AlunoEntity> listaPaginada = new PageImpl<>(List.of(aluno), PageRequest.of(numeroPagina, tamanho), 0);
        when(alunoRepository.findAllByAtivo(any(), any(Pageable.class))).thenReturn(listaPaginada);
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarAlunoPaginado(idAluno,nome, email, numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(1, alunoDTOPageDTO.getTotalElementos());
        assertEquals(1, alunoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), alunoDTOPageDTO.getPagina());
    }

    @Test
    public void DeveListarAlunoPaginadoComListaVazia() throws RegraDeNegocioException {
        final int numeroPagina = 0;
        final int tamanho = 0;
        final Integer idAluno = null;
        final String nome = null;
        final String email = null;
        List<AlunoDTO> listaVazia = new ArrayList<>();
        PageDTO<AlunoDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<AlunoDTO> paginaRecebida = alunoService.listarAlunoPaginado(idAluno,nome, email, numeroPagina, tamanho);
        assertEquals(paginaRecebida, alunoDTOPageDTOEsperado);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void DeveListarAlunoPaginadoComListaErroDeValidacaoDosValoresDeSizeEpage() throws RegraDeNegocioException {
        final int numeroPagina = -1;
        final int tamanho = -1;
        final Integer idAluno = null;
        final String nome = null;
        final String email = null;
        alunoService.listarAlunoPaginado(idAluno,nome, email, numeroPagina, tamanho);

    }

//    @Test
//    public void deveTestarCadastroAlunoComSucesso() throws RegraDeNegocioException {
//
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
//        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());
//
//        when(alunoRepository.save(any())).thenReturn(aluno);
//
//        AlunoDTO alunoDTO = alunoService.cadastrarAluno(alunoCreateDTO, Stack.BACKEND);
//
//        assertEquals(aluno.getNome(), alunoDTO.getNome());
//        assertEquals(aluno.getIdAluno(), alunoDTO.getIdAluno());
//        assertEquals(aluno.getStack(), alunoDTO.getStack());
//        assertNotNull(alunoDTO);
//    }
//
//
//    @Test
//    public void deveTestarUploadImagemComSucesso() throws RegraDeNegocioException {
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        byte[] imagemBytes = new byte[10 * 1024];
//        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
//        Integer idAluno = 1;
//
//        when(alunoRepository.findByAtivoAndIdAluno(any(), anyInt())).thenReturn(Optional.of(aluno));
//        when(alunoRepository.save(any())).thenReturn(aluno);
//
//        AlunoDTO alunoDTO = alunoService.uploadImagem(imagem, idAluno);
//
//
//        assertNotEquals(aluno.getFoto(), alunoDTO.getFoto());
//        assertNotNull(alunoDTO);
//
//    }
//
//    @Test
//    public void deveTestarFindByIdDTO() throws RegraDeNegocioException {
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        Integer idAluno = 1;
//
//        when(alunoRepository.findByAtivoAndIdAluno(Ativo.S, aluno.getIdAluno())).thenReturn(Optional.of(aluno));
//        AlunoDTO alunoDTO = alunoService.findByIdDTO(idAluno);
//
//        assertNotNull(alunoDTO);
//
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
//        Integer idAluno = 1;
//        when(alunoRepository.findByAtivoAndIdAluno(any(), anyInt())).thenReturn(Optional.empty());
//        alunoService.findById(idAluno);
//
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestaCadastrarAlunoComErro() throws RegraDeNegocioException {
//        AlunoCreateDTO alunoCreateDTO = null;
//        alunoService.cadastrarAluno(alunoCreateDTO, Stack.BACKEND);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarUploadImagemComErro() throws RegraDeNegocioException {
//        byte[] imagemBytes = null;
//        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
//        Integer idAluno = 1;
//
//        alunoService.uploadImagem(imagem, idAluno);
//
//    }
//
//    @Test
//    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
//        Integer idAluno = 1;
//        AlunoEntity alunoFactory = AlunoFactory.getAlunoEntity();
//        when(alunoRepository.findByAtivoAndIdAluno(Ativo.S, idAluno)).thenReturn(Optional.of(alunoFactory));
//        AlunoEntity alunoEntity = alunoService.findById(idAluno);
//        assertEquals(alunoEntity, alunoFactory);
//        assertNotNull(alunoEntity);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarAtualizarAlunoPorIdComErro() throws RegraDeNegocioException {
//
//        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
//        Integer idAluno = 1;
//        alunoService.atualizarAlunoPorId(idAluno, alunoCreateDTO, Stack.BACKEND);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarAtualizarAlunoPorIdComErroDeEmailJaLocalizadoNoBanco() throws RegraDeNegocioException {
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        aluno.setEmail("teste@gmail.com.br");
//        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
//        alunoCreateDTO.setEmail("teste@gmail.com.br");
//        Integer idAluno = 1;
//        when(alunoService.findById(aluno.getIdAluno())).thenReturn(aluno);
//        alunoService.atualizarAlunoPorId(idAluno, alunoCreateDTO, Stack.BACKEND);
//    }
//
//    @Test
//    public void deveTestarAtualizarAlunoComSucesso() throws RegraDeNegocioException {
//
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        aluno.setEmail("teste@gmail.com.br");
//        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
//        alunoCreateDTO.setEmail("diferente@gmail.com.br");
//
//        when(alunoRepository.findByAtivoAndIdAluno(any(), anyInt())).thenReturn(Optional.of(aluno));
////        when(alunoService.findById(aluno.getIdAluno())).thenReturn(aluno);
//        when(alunoRepository.save(any())).thenReturn(aluno);
//
//        AlunoDTO alunoDTO = alunoService.atualizarAlunoPorId(aluno.getIdAluno(), alunoCreateDTO, aluno.getStack());
//
//        assertNotNull(alunoDTO);
//    }
//
//    @Test
//    public void deveTestarDesativarAlunoByIdComSucesso() throws RegraDeNegocioException {
//        Integer id = 1;
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//
//        when(alunoRepository.findByAtivoAndIdAluno(Ativo.S, aluno.getIdAluno())).thenReturn(Optional.of(aluno));
//        when(alunoRepository.save(any())).thenReturn(aluno);
//
//        alunoService.desativarAlunoById(id);
//
//        verify(alunoRepository, times(1)).save(aluno);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveLancarExcecaoAoExecutartransformarImagemEmBytesComIOException() throws RegraDeNegocioException, IOException {
//        final MultipartFile imagem = Mockito.mock(MultipartFile.class, Mockito.RETURNS_DEEP_STUBS);
//        when(imagem.getBytes()).thenThrow(new IOException("Teste"));
//        alunoService.transformarImagemEmBytes(imagem);
//    }
//
//    @Test
//    public void deveTestarExcluirAlunoTesteComSucesso() throws RegraDeNegocioException {
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        when(alunoRepository.findByAtivoAndIdAluno(any(),anyInt())).thenReturn(Optional.of(aluno));
//
//        alunoService.excluirAlunosTeste(1);
//        verify(alunoRepository, times(1)).delete(aluno);
//    }
//
//    private static UsernamePasswordAuthenticationToken getAuthentication() {
//        return new UsernamePasswordAuthenticationToken(1,
//                null,
//                Collections.emptyList());
//    }
}

