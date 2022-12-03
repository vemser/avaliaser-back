package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioRetornoAvaliacaoFeedbackDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AcompanhamentoService acompanhamentoService;
    private final UsuarioService usuarioService;
    private final AlunoService alunoService;
    private final ObjectMapper objectMapper;

    public AvaliacaoDTO cadastrarAvaliacao(AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacao = new AvaliacaoEntity();
        AlunoEntity aluno = alunoService.findById(avaliacaoCreateDTO.getIdAluno());
        AcompanhamentoEntity acompanhamento = acompanhamentoService.findById(avaliacaoCreateDTO.getIdAcompanhamento());
        UsuarioEntity usuario = usuarioService.findById(usuarioService.getLoggedUser().getIdUsuario());

        avaliacao.setUsuarioEntity(usuario);
        avaliacao.setAlunoEntity(aluno);
        avaliacao.setAcompanhamentoEntity(acompanhamento);
        avaliacao.setDescricao(avaliacaoCreateDTO.getDescricao());
        avaliacao.setTipo(avaliacaoCreateDTO.getTipo());
        avaliacao.setDataCriacao(avaliacaoCreateDTO.getDataCriacao());
        AvaliacaoEntity avaliacaoCriada = avaliacaoRepository.save(avaliacao);

        return converterParaAvaliacaoDTO(avaliacaoCriada);
    }

    public AvaliacaoDTO editarAvaliacao(Integer id, EditarAvaliacaoDTO editarAvaliacaoDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacao = findById(id);
        AlunoEntity aluno = alunoService.findById(editarAvaliacaoDTO.getIdAluno());
        AcompanhamentoEntity acompanhamento = acompanhamentoService.findById(editarAvaliacaoDTO.getIdAcompanhamento());

        avaliacao.setAlunoEntity(aluno);
        avaliacao.setAcompanhamentoEntity(acompanhamento);
        avaliacao.setDescricao(editarAvaliacaoDTO.getDescricao());
        avaliacao.setTipo(editarAvaliacaoDTO.getStatus());

        AvaliacaoEntity avaliacaoCriada = avaliacaoRepository.save(avaliacao);

        return converterParaAvaliacaoDTO(avaliacaoCriada);
    }

    public PageDTO<AvaliacaoDTO> listarAvaliacoesPaginados(Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AvaliacaoEntity> paginaDoRepositorio = avaliacaoRepository.findAll(pageRequest);
            List<AvaliacaoDTO> avaliacoesDTO = paginaDoRepositorio.getContent().stream()
                    .map(this::converterParaAvaliacaoDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    page,
                    size,
                    avaliacoesDTO
            );
        }
        List<AvaliacaoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public PageDTO<AvaliacaoDTO> listarAvaliacoesPorAlunoPaginados(Integer idAluno, Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        alunoService.findById(idAluno);
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AvaliacaoEntity> paginaDoRepositorio = avaliacaoRepository.findAllByIdAluno(idAluno, pageRequest);
            List<AvaliacaoDTO> avaliacoesDTO = paginaDoRepositorio.getContent().stream()
                    .map(this::converterParaAvaliacaoDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    page,
                    size,
                    avaliacoesDTO
            );
        }
        List<AvaliacaoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public AvaliacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return avaliacaoRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Avaliação não encontrada."));
    }

    public AvaliacaoDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(id);
        AvaliacaoDTO avaliacaoDTO =
                objectMapper.convertValue(avaliacaoEntity, AvaliacaoDTO.class);
        return avaliacaoDTO;
    }
    private AvaliacaoDTO converterParaAvaliacaoDTO(AvaliacaoEntity avaliacao) {

        AvaliacaoDTO avaliacaoDTO = objectMapper.convertValue(avaliacao, AvaliacaoDTO.class);
        avaliacaoDTO.setAcompanhamento(objectMapper.convertValue(avaliacao.getAcompanhamentoEntity(), AcompanhamentoDTO.class));
        avaliacaoDTO.setAluno(objectMapper.convertValue(avaliacao.getAlunoEntity(), AlunoDTO.class));
        avaliacaoDTO.setResponsavel(objectMapper.convertValue(avaliacao.getUsuarioEntity(), UsuarioRetornoAvaliacaoFeedbackDTO.class));
        return avaliacaoDTO;

    }
}
