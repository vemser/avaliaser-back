package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.FeedBackRepository;
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

    public PageDTO<FeedBackDTO> listarFeedBackPaginados(Integer pagina, Integer tamanho) {
        if(tamanho != 0){
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<FeedBackEntity> paginaDoRepositorio = feedBackRepository.findAll(pageRequest);
            List<FeedBackDTO> acompanhamentoPaginas = paginaDoRepositorio.getContent().stream()
                    .map(acompanhamentoEntity -> objectMapper.convertValue(acompanhamentoEntity, FeedBackDTO.class))
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    acompanhamentoPaginas
            );}
        List<FeedBackDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L,0,0,tamanho,  listaVazia);
    }

    public PageDTO<FeedBackDTO> listarFeedBackPorAlunoPaginados(Integer id,Integer pagina, Integer tamanho) {
        if(tamanho != 0){
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<FeedBackEntity> paginaDoRepositorio = feedBackRepository.findAllByIdAluno(id,pageRequest);
            List<FeedBackDTO> acompanhamentoPaginas = paginaDoRepositorio.getContent().stream()
                    .map(acompanhamentoEntity -> objectMapper.convertValue(acompanhamentoEntity, FeedBackDTO.class))
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    acompanhamentoPaginas
            );}
        List<FeedBackDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L,0,0,tamanho,  listaVazia);
    }

    public FeedBackDTO cadastrarFeedBack(FeedBackCreateDTO feedBackCreateDTO) throws RegraDeNegocioException {

        FeedBackEntity feedBackEntity = new FeedBackEntity();
        UsuarioEntity usuarioEntity = usuarioService.getLoggedUser();
        AlunoEntity alunoEntity = alunoService.findById(feedBackCreateDTO.getIdAluno());

        AlunoDTO alunoDTO = objectMapper.convertValue(alunoEntity, AlunoDTO.class);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);

        feedBackEntity.setTipo(feedBackCreateDTO.getTipo());
        feedBackEntity.setUsuarioEntity(usuarioEntity);
        feedBackEntity.setAlunoEntity(alunoEntity);
        feedBackEntity.setDescricao(feedBackCreateDTO.getDescricao());

        FeedBackEntity feedBackSalvo = feedBackRepository.save(feedBackEntity);
        FeedBackDTO feedBackDTO = objectMapper.convertValue(feedBackSalvo, FeedBackDTO.class);

        feedBackDTO.setAlunoDTO(alunoDTO);
        feedBackDTO.setUsuarioDTO(usuarioDTO);

        return feedBackDTO;
    }

//    public FeedBackDTO findAll() throws RegraDeNegocioException {
//        List<FeedBackEntity> feedBackEntities = feedBackRepository.findAll();
//        List<FeedBackDTO> feedBackDTOS = feedBackEntities.stream()
//                .map(feedBackEntity -> objectMapper.convertValue(feedBackEntity,FeedBackDTO.class)).toList();
//        feedBackDTOS.stream().map(feedBackDTO -> );
//
//    }
//    public FeedBackDTO buscaObjetosDTO(){
//        UsuarioEntity usuarioEntity = usuarioService.getLoggedUser();
//        AlunoEntity alunoEntity = alunoService.findById(feedBackCreateDTO.getIdAluno());
//        AlunoDTO alunoDTO = objectMapper.convertValue(alunoEntity, AlunoDTO.class);
//        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
//    }

    public FeedBackDTO editarFeedBack(Integer id,EditarFeedBackDTO editarFeedBackDTO) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        AlunoEntity alunoEntity = alunoService.findById(editarFeedBackDTO.getIdAluno());
        feedBackEntity.setAlunoEntity(alunoEntity);
        feedBackEntity.setDescricao(editarFeedBackDTO.getDescricao());
        feedBackEntity.setTipo(editarFeedBackDTO.getTipo());
        feedBackRepository.save(feedBackEntity);
        return objectMapper.convertValue(feedBackEntity, FeedBackDTO.class);
    }

    public FeedBackEntity findById(Integer id) throws RegraDeNegocioException {
        return feedBackRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("FeedBack não encontrado."));
    }

    public FeedBackDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        FeedBackEntity feedBackEntity = findById(id);
        FeedBackDTO feedBackDTO =
                objectMapper.convertValue(feedBackEntity, FeedBackDTO.class);
        return feedBackDTO;
    }
}
