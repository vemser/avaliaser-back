package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioRetornoAvaliacaoFeedbackDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.FeedBackRepository;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedBackRepository feedBackRepository;
    private final AlunoService alunoService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public PageDTO<FeedBackDTO> listarFeedBackPaginados(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if(tamanho < 0 || pagina < 0 ){
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if(tamanho > 0){
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<FeedBackEntity> paginaDoRepositorio = feedBackRepository.findAll(pageRequest);
            List<FeedBackDTO> feedbackList = paginaDoRepositorio.getContent().stream()
                    .map(this::converterParaFeedbackDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    feedbackList
            );}
        List<FeedBackDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L,0,0,tamanho,  listaVazia);
    }

    public PageDTO<FeedBackDTO> listarFeedBackPorAlunoPaginados(Integer id,Integer pagina, Integer tamanho) throws RegraDeNegocioException {
            alunoService.findById(id);
                if (tamanho < 0 || pagina < 0) {
                    throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
                }

            if (tamanho > 0) {
                PageRequest pageRequest = PageRequest.of(pagina, tamanho);
                Page<FeedBackEntity> paginaDoRepositorio = feedBackRepository.findAllByIdAluno(id, pageRequest);
                List<FeedBackDTO> feedbackList = paginaDoRepositorio.getContent().stream()
                        .map(this::converterParaFeedbackDTO)
                        .toList();

                return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                        paginaDoRepositorio.getTotalPages(),
                        pagina,
                        tamanho,
                        feedbackList
                );
            }
            List<FeedBackDTO> listaVazia = new ArrayList<>();
            return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

    }

    public FeedBackDTO cadastrarFeedBack(FeedBackCreateDTO feedBackCreateDTO) throws RegraDeNegocioException {

        FeedBackEntity feedBackEntity = new FeedBackEntity();
        UsuarioEntity usuarioEntity = usuarioService.getLoggedUser();
        AlunoEntity alunoEntity = alunoService.findById(feedBackCreateDTO.getIdAluno());

        feedBackEntity.setTipo(feedBackCreateDTO.getTipo());
        feedBackEntity.setUsuarioEntity(usuarioEntity);
        feedBackEntity.setAlunoEntity(alunoEntity);
        feedBackEntity.setDescricao(feedBackCreateDTO.getDescricao());

        FeedBackEntity feedBackSalvo = feedBackRepository.save(feedBackEntity);
        FeedBackDTO feedBackDTO = converterParaFeedbackDTO(feedBackSalvo);

        return feedBackDTO;
    }


    public FeedBackDTO editarFeedBack(Integer id,EditarFeedBackDTO editarFeedBackDTO) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        AlunoEntity alunoEntity = alunoService.findById(editarFeedBackDTO.getIdAluno());
        feedBackEntity.setAlunoEntity(alunoEntity);
        feedBackEntity.setDescricao(editarFeedBackDTO.getDescricao());
        feedBackEntity.setTipo(editarFeedBackDTO.getTipo());
        FeedBackDTO feedBackDTO = converterParaFeedbackDTO(feedBackRepository.save(feedBackEntity));
        return feedBackDTO;
    }

    public FeedBackEntity findById(Integer id) throws RegraDeNegocioException {
        return feedBackRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("FeedBack não encontrado."));
    }

    public FeedBackDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        return converterParaFeedbackDTO(feedBackEntity);
    }
    public FeedBackDTO converterParaFeedbackDTO(FeedBackEntity feedback){
        FeedBackDTO feedBackDTO = objectMapper.convertValue(feedback, FeedBackDTO.class);
        feedBackDTO.setAlunoDTO(objectMapper.convertValue(feedback.getAlunoEntity(), AlunoDTO.class));
        if(feedback.getAlunoEntity().getFoto() != null) {
            feedBackDTO.getAlunoDTO().setFoto(ImageUtil.decompressImage(feedback.getAlunoEntity().getFoto()));
        }
        feedBackDTO.setUsuarioDTO(objectMapper.convertValue(feedback.getUsuarioEntity(), UsuarioRetornoAvaliacaoFeedbackDTO.class));
        return feedBackDTO;
    }
}
