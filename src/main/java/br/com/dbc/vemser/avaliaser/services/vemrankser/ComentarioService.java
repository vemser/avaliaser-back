package br.com.dbc.vemser.avaliaser.services.vemrankser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadecomentariodto.AtividadeComentarioAvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadecomentariodto.AtividadeComentarioAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.comentariodto.ComentarioDTO;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.entities.ComentarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.ComentarioRepository;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final AtividadeService atividadeService;
    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;

    public AtividadeComentarioAvaliacaoDTO adicionarComentarioAvaliar(AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, Integer idAluno, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);
        UsuarioEntity usuarioEntity = usuarioService.findById(idAluno);

        ComentarioEntity comentarioEntity = objectMapper.convertValue(atividadeComentarioAvaliacaoCreateDTO, ComentarioEntity.class);
        comentarioEntity.setIdAtividade(atividadeEntity.getIdAtividade());
        comentarioEntity.setAtividade(atividadeEntity);
        comentarioEntity.setIdUsuario(usuarioEntity.getIdUsuario());
        comentarioEntity.setUsuario(usuarioEntity);
        comentarioEntity.setComentario(comentarioEntity.getComentario());
        atividadeEntity.setPontuacao(atividadeComentarioAvaliacaoCreateDTO.getNotaAvalicao());
        atividadeEntity.getAlunos().stream()
                .filter(usuarioEntity1 -> usuarioEntity1.getIdUsuario().equals(usuarioEntity.getIdUsuario()))
                .forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeEntity)));

        comentarioEntity.setComentario(atividadeComentarioAvaliacaoCreateDTO.getComentario());
        comentarioRepository.save(comentarioEntity);
        atividadeService.save(atividadeEntity);

        return objectMapper.convertValue(atividadeComentarioAvaliacaoCreateDTO, AtividadeComentarioAvaliacaoDTO.class);
    }

    public ComentarioDTO adicionarFeedback(ComentarioDTO comentarioDTO, Integer idAluno, Situacao situacao) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(idAluno);
        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioDTO, ComentarioEntity.class);
        comentarioEntity.setStatusComentario(situacao.getSituacao());
        comentarioEntity.setIdUsuario(usuarioEntity.getIdUsuario());
        comentarioEntity.setUsuario(usuarioEntity);
        comentarioRepository.save(comentarioEntity);
        return comentarioDTO;
    }

    public PageDTO<ComentarioDTO> comentariosDoAluno(Integer pagina, Integer tamanho, Integer idAluno) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<ComentarioEntity> page = comentarioRepository.findAllByIdUsuario(pageRequest, idAluno);
            List<ComentarioDTO> pessoasDaPagina = page.getContent().stream()
                    .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ComentarioDTO.class))
                    .toList();
            return new PageDTO<>(page.getTotalElements(),
                    page.getTotalPages(),
                    pagina,
                    tamanho,
                    pessoasDaPagina
            );
        }
        List<ComentarioDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public Integer calcularPontuacao(UsuarioEntity usuarioEntity, AtividadeEntity atividadeEntity) {
        return usuarioEntity.getPontuacaoAluno() + atividadeEntity.getPontuacao();
    }

    public List<ComentarioDTO> listarComentarioPorAtividade(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividade = atividadeService.buscarPorIdAtividade(idAtividade);

        return comentarioRepository.findAllByIdAtividade(atividade.getIdAtividade())
                .stream()
                .map(atividadeDto -> objectMapper.convertValue(atividadeDto, ComentarioDTO.class))
                .toList();
    }

    public List<ComentarioDTO> listarComentarioPorFeedback(Situacao situacao) {
        return comentarioRepository.findAllByStatusComentario(situacao.getSituacao())
                .stream()
                .map(comentarioEntity -> objectMapper.convertValue(comentarioEntity, ComentarioDTO.class))
                .toList();
    }

    public ComentarioEntity buscarPorIdComentario(Integer idComentario) throws RegraDeNegocioException {
        return comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RegraDeNegocioException("Comentario não encontrado"));
    }

    public void desativarComentario(Integer idComentario) throws RegraDeNegocioException {
        ComentarioEntity comentario = findById(idComentario);
        comentario.setSituacao(Situacao.INATIVO);
        comentarioRepository.save(comentario);
    }

    public void delete(Integer idComentario) throws RegraDeNegocioException {

        ComentarioEntity comentarioRecuperado = findById(idComentario);
        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioRecuperado, ComentarioEntity.class);
        comentarioRepository.delete(comentarioEntity);

    }

    public ComentarioEntity findById(Integer id) throws RegraDeNegocioException {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("o comentario nao foi encontrado"));
    }
}
