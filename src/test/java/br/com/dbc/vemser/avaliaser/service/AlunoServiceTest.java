package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.AlunoFactory;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import br.com.dbc.vemser.avaliaser.services.allocation.TecnologiaService;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.mapping.Any;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
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

    @Mock
    private ProgramaService programaService;

    @Mock
    private TrilhaService trilhaService;

    @Mock
    private TecnologiaService tecnologiaService;

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

    @Test
    public void deveTestarCadastroAlunoComSucesso() throws RegraDeNegocioException {

        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());

        when(programaService.findByIdAtivo(any())).thenReturn(new ProgramaEntity());
        when(trilhaService.findById(any())).thenReturn(new TrilhaEntity());
        when(tecnologiaService.findByIdTecnologia(any())).thenReturn(new TecnologiaEntity());
        when(alunoRepository.save(any())).thenReturn(aluno);

        AlunoDTO alunoDTO = alunoService.cadastrarAluno(alunoCreateDTO);

        assertEquals(aluno.getNome(), alunoDTO.getNome());
        assertEquals(aluno.getIdAluno(), alunoDTO.getIdAluno());

        assertNotNull(alunoDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCadastroAlunoComErroEmail() throws RegraDeNegocioException {

        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());

        when(programaService.findByIdAtivo(any())).thenReturn(new ProgramaEntity());
        when(trilhaService.findById(any())).thenReturn(new TrilhaEntity());
        when(tecnologiaService.findByIdTecnologia(any())).thenReturn(new TecnologiaEntity());
        when(alunoRepository.save(any())).thenReturn(Optional.empty());

        alunoService.cadastrarAluno(alunoCreateDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarCadastroAlunoComErro() throws RegraDeNegocioException {

        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());

        when(programaService.findByIdAtivo(any())).thenReturn(new ProgramaEntity());
        when(trilhaService.findById(any())).thenThrow(new RegraDeNegocioException("Trilha não encontrada."));

        alunoService.cadastrarAluno(alunoCreateDTO);

    }


    @Test
    public void deveTestarFindByEmailComSucesso() throws RegraDeNegocioException {
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        String email = "paulo.sergio";

        when(alunoRepository.findByEmail(any())).thenReturn(Optional.of(aluno));
        AlunoDTO alunoDTO = alunoService.findByEmail(email);

        assertNotNull(alunoDTO);
        assertEquals("paulo.sergio@dbccompany.com.br", alunoDTO.getEmail());

    }
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
    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAtualizarAlunoPorIdComErro() throws RegraDeNegocioException {
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
        Integer idAluno = 1;
        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));
        when(programaService.findByIdAtivo(any())).thenReturn(new ProgramaEntity());
        when(trilhaService.findById(any())).thenThrow(new RegraDeNegocioException("Trilha não encontrada."));
        alunoService.atualizarAlunoPorId(idAluno, alunoCreateDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAtualizarAlunoPorIdComErroDeEmailJaLocalizadoNoBanco() throws RegraDeNegocioException {
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();

        Integer idAluno = 1;
        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));
        when(programaService.findByIdAtivo(any())).thenReturn(new ProgramaEntity());
        when(trilhaService.findById(any())).thenReturn(new TrilhaEntity());
        alunoService.atualizarAlunoPorId(idAluno, alunoCreateDTO);
    }
//
//    @Test
//    public void deveTestarAtualizarAlunoComSucesso() throws RegraDeNegocioException {
//
//        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
//        AlunoCreateDTO alunoCreateDTO = AlunoFactory.getAlunoCreateDTO();
//
//        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));
//        when(programaService.findByIdAtivo(any())).thenReturn(new ProgramaEntity());
//        when(trilhaService.findById(any())).thenReturn(new TrilhaEntity());
//        when(tecnologiaService.findByIdTecnologia(any())).thenReturn(new TecnologiaEntity());
//        when(alunoRepository.save(any())).thenReturn(aluno);
//
//        AlunoDTO alunoDTO = alunoService.atualizarAlunoPorId(1, alunoCreateDTO);
//
//        assertNotNull(alunoDTO);
//    }
//
    @Test
    public void deveTestarDesativarAlunoByIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();

        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any())).thenReturn(aluno);

        alunoService.desativarAlunoById(id);

        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    public void deveTestarListarAlunosDisponiveisComSucesso() throws RegraDeNegocioException{
        final int numeroPagina = 0;
        final int tamanho = 3;


        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        PageImpl<AlunoEntity> listaPaginada = new PageImpl<>(List.of(aluno), PageRequest.of(numeroPagina, tamanho), 0);
        when(alunoRepository.findAllBySituacao(any(Pageable.class), any())).thenReturn(listaPaginada);
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarDisponiveis(numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(1, alunoDTOPageDTO.getTotalElementos());
        assertEquals(1, alunoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), alunoDTOPageDTO.getPagina());
    }
    @Test
    public void deveTestarListarAlunosDisponiveisComListaVazia() throws RegraDeNegocioException{
        final int numeroPagina = 0;
        final int tamanho = 0;


        List<AlunoDTO> listaVazia = new ArrayList<>();
        PageDTO<AlunoDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarDisponiveis(numeroPagina, tamanho);

        assertNotNull(alunoDTOPageDTO);
        assertEquals(alunoDTOPageDTO, alunoDTOPageDTOEsperado);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarListarAlunosDisponiveisComErro() throws RegraDeNegocioException{
        final int numeroPagina = 0;
        final int tamanho =-1;
        alunoService.listarDisponiveis(numeroPagina, tamanho);

    }

    @Test
    public void deveTestarfindByIdDTOcomSucesso() throws RegraDeNegocioException{
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();

        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));

        AlunoDTO alunoDTO = alunoService.findByIdDTO(1);

        assertNotNull(alunoDTO);
    }

    @Test
    public void deveTestarAlterarStatuscomSucesso() throws RegraDeNegocioException{
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();

        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));
        aluno.setSituacao(SituacaoReserva.ALOCADO);
        when(alunoRepository.save(any())).thenReturn(aluno);

        AlunoEntity alunoEntity = alunoService.alterarStatusAluno(1, SituacaoReserva.ALOCADO);


        assertEquals(SituacaoReserva.ALOCADO, alunoEntity.getSituacao());
    }



}

