package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.FeedBackRepository;
import br.com.dbc.vemser.avaliaser.services.vemrankser.ModuloService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedBackRepository feedBackRepository;
    private final AlunoService alunoService;
    private final ModuloService moduloService;
    private final ObjectMapper objectMapper;


    public PageDTO<FeedBackDTO> listarFeedBackPaginados(Integer idFeedBack, Integer idAluno, String nome, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            Page<FeedBackEntity> paginaDoRepositorio = filtrarFeed(idFeedBack, idAluno, nome, pagina, tamanho);
            List<FeedBackDTO> feedBackDTOS = paginaDoRepositorio.getContent().stream()
                    .map(this::converterParaFeedbackDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    feedBackDTOS);
        }
        List<FeedBackDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public FeedBackDTO cadastrarFeedBack(FeedBackCreateDTO feedBackCreateDTO) throws RegraDeNegocioException {

        FeedBackEntity feedBackEntity = new FeedBackEntity();
        AlunoEntity alunoEntity = alunoService.findById(feedBackCreateDTO.getIdAluno());
        ModuloEntity moduloEntity = moduloService.buscarPorIdModulo(feedBackCreateDTO.getIdModulo());
        feedBackEntity.setNomeInstrutor(feedBackCreateDTO.getUsuarioLogado());
        feedBackEntity.setSituacao(feedBackCreateDTO.getSituacao());
        feedBackEntity.setDescricao(feedBackCreateDTO.getDescricao());
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        feedBackEntity.setData(now.toLocalDate());
        feedBackEntity.setAtivo(Ativo.S);
        feedBackEntity.setAlunoEntity(alunoEntity);
        feedBackEntity.setModuloEntity(moduloEntity);
        FeedBackEntity feedBackSalvo = feedBackRepository.save(feedBackEntity);
        FeedBackDTO feedBackDTO = converterParaFeedbackDTO(feedBackSalvo);

        return feedBackDTO;
    }


    public FeedBackDTO editarFeedBack(Integer id, EditarFeedBackDTO editarFeedBackDTO) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        feedBackEntity.setDescricao(editarFeedBackDTO.getDescricao());
        feedBackEntity.setSituacao(editarFeedBackDTO.getSituacao());
        FeedBackDTO feedBackDTO = converterParaFeedbackDTO(feedBackRepository.save(feedBackEntity));
        return feedBackDTO;
    }

    public void desativarFeed(Integer id) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        feedBackEntity.setAtivo(Ativo.N);
        feedBackRepository.save(feedBackEntity);
    }

    public FeedBackEntity findById(Integer id) throws RegraDeNegocioException {
        return feedBackRepository.findByIdFeedBackAndAtivo(id, Ativo.S).orElseThrow(
                () -> new RegraDeNegocioException("FeedBack não encontrado."));
    }

    public FeedBackDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        return converterParaFeedbackDTO(feedBackEntity);
    }

    public List<FeedBackDTO> findUsuarios(String usuario) {
        Set<FeedBackEntity> feedBackDTOS = feedBackRepository.findAllByNomeInstrutorContainsIgnoreCase(usuario);
        if (feedBackDTOS.isEmpty() || feedBackDTOS == null) {
            return Collections.emptyList();
        }
        return feedBackDTOS.stream()
                .map(feedBackEntity -> converterParaFeedbackDTO(feedBackEntity))
                .toList();
    }

    private Page<FeedBackEntity> filtrarFeed(Integer idFeedBack, Integer idAluno, String nome, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idAluno == null)) {
            return feedBackRepository.findAllByIdAlunoAndAtivo(idAluno, Ativo.S, pageRequest);
        } else if (!(idFeedBack == null)) {
            return feedBackRepository.findByIdFeedBackAndAtivo(idFeedBack, Ativo.S, pageRequest);
        } else if (!(nome == null)) {
            return feedBackRepository.findAllByAlunoEntity_NomeContainingIgnoreCaseAndAtivo(nome, Ativo.S, pageRequest);
        }
        return feedBackRepository.findAllByAtivo(pageRequest, Ativo.S);
    }

    public FeedBackDTO converterParaFeedbackDTO(FeedBackEntity feedback) {
        FeedBackDTO feedBackDTO = objectMapper.convertValue(feedback, FeedBackDTO.class);
        feedBackDTO.setAlunoDTO(alunoService.converterAlunoDTO(feedback.getAlunoEntity()));
        feedBackDTO.setModuloDTO(moduloService.converterEmDTO(feedback.getModuloEntity()));
        return feedBackDTO;
    }
}
