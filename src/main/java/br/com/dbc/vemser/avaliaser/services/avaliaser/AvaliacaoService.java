package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AcompanhamentoService acompanhamentoService;

    private final AlunoService alunoService;
    private final ObjectMapper objectMapper;

    public AvaliacaoDTO create(AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamento = acompanhamentoService.findById(avaliacaoCreateDTO.getIdAcompanhamento());
        acompanhamentoService.verificarAcompanhamentoInativo(acompanhamento);
        AlunoEntity aluno = alunoService.findById(avaliacaoCreateDTO.getIdAluno());
        alunoService.verificarAlunoInativo(aluno);

        if (avaliacaoCreateDTO.getDataCriacao() == null) {
            avaliacaoCreateDTO.setDataCriacao(LocalDate.now());
        }

        AvaliacaoEntity avaliacao = converterEmEntity(avaliacaoCreateDTO, acompanhamento, aluno);
        AvaliacaoEntity avaliacaoCriada = avaliacaoRepository.save(avaliacao);

        return converterEmDTO(avaliacaoCriada);
    }

    public AvaliacaoDTO update(Integer id, EditarAvaliacaoDTO editarAvaliacaoDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacao = findById(id);

        avaliacao.setDescricao(editarAvaliacaoDTO.getDescricao());
        avaliacao.setTipoAvaliacao(editarAvaliacaoDTO.getTipoAvaliacao());

        if (editarAvaliacaoDTO.getDataCriacao() != null) {
            avaliacao.setDataCriacao(editarAvaliacaoDTO.getDataCriacao());
        } else {
            avaliacao.setDataCriacao(LocalDate.now());
        }
        return converterEmDTO(avaliacaoRepository.save(avaliacao));
    }

    public void desativar(Integer id) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(id);
        avaliacaoEntity.setAtivo(Ativo.N);
        avaliacaoRepository.save(avaliacaoEntity);
    }

//    public PageDTO<AvaliacaoDTO> listarAvaliacaoPorAcompanhamentoAlunoSituacao(Integer idAcompanhamento,
//                                                                               String tituloAcompanhamento,
//                                                                               Integer idAluno,
//                                                                               Ativo ativo,
//                                                                               Integer pagina,
//                                                                               Integer tamanho) throws RegraDeNegocioException {
//        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
//        if (tamanho < 0 || pagina < 0) {
//            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
//        }
//        if (tamanho > 0) {
//            Page<AvaliacaoEntity> acompanhamentoEntitie = avaliacaoRepository.findAllByAcompanhamentoEntity_IdAcompanhamentoOOrAcompanhamentoEntity_TituloContainingIgnoreCaseOrIdAlunoOrAcompanhamentoEntity_Ativo(idAcompanhamento,
//                    tituloAcompanhamento,
//                    idAluno,
//                    ativo,
//                    pageRequest);
//            List<AvaliacaoDTO> acompanhamentoPaginas = acompanhamentoEntitie.getContent().stream()
//                    .map(this::converterEmDTO)
//                    .toList();
//
//            return new PageDTO<>(acompanhamentoEntitie.getTotalElements(),
//                    acompanhamentoEntitie.getTotalPages(),
//                    pagina,
//                    tamanho,
//                    acompanhamentoPaginas
//            );
//        }
//        List<AvaliacaoDTO> listaVazia = new ArrayList<>();
//        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
//    }

    public PageDTO<AvaliacaoDTO> listarAvaliacaoPaginados(Integer idAvaliacao,
                                                          TipoAvaliacao tipoAvaliacao,
                                                          String tituloAcompanhamento,
                                                          String nomeAluno,
                                                          Integer pagina,
                                                          Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            Page<AvaliacaoEntity> paginaDoRepositorio = filtrarAvaliacao(idAvaliacao, tipoAvaliacao, tituloAcompanhamento, nomeAluno, pagina, tamanho);
            List<AvaliacaoDTO> acoavaliacaoDTOList = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    acoavaliacaoDTOList
            );
        }
        List<AvaliacaoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    private Page<AvaliacaoEntity> filtrarAvaliacao(Integer idAvaliacao, TipoAvaliacao tipoAvaliacao, String tituloAcompanhamento, String nomeAluno, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(tipoAvaliacao == null)) {
            return avaliacaoRepository.findAllByTipoAvaliacaoAndAtivo(tipoAvaliacao, Ativo.S, pageRequest);
        } else if (!(tituloAcompanhamento == null)) {
            return avaliacaoRepository.findAllByAcompanhamentoEntity_TituloContainingIgnoreCaseAndAtivo(tituloAcompanhamento, Ativo.S, pageRequest);
        } else if (!(nomeAluno == null)) {
            return avaliacaoRepository.findAllByAlunoEntity_NomeContainingIgnoreCaseAndAtivo(nomeAluno, Ativo.S, pageRequest);
        } else if (!(idAvaliacao == null)) {
            return avaliacaoRepository.findByIdAvaliacaoAndAtivo(idAvaliacao,Ativo.S,pageRequest);
        }
        return avaliacaoRepository.findAllByAtivo(Ativo.S, pageRequest);
    }

    public PageDTO<AvaliacaoDTO> listarAvaliacoes(Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AvaliacaoEntity> paginaDoRepositorio = avaliacaoRepository.findAllByAtivo(Ativo.S, pageRequest);
            List<AvaliacaoDTO> avaliacoesDTO = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
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

//    public PageDTO<AvaliacaoDTO> listarAvaliacoesPorAlunoPaginados(Integer idAluno, Integer page, Integer size) throws RegraDeNegocioException {
//        if (size < 0 || page < 0) {
//            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
//        }
//        alunoService.findById(idAluno);
//        if (size > 0) {
//            PageRequest pageRequest = PageRequest.of(page, size);
//            Page<AvaliacaoEntity> paginaDoRepositorio = avaliacaoRepository.findAllByIdAluno(idAluno, pageRequest);
//            List<AvaliacaoDTO> avaliacoesDTO = paginaDoRepositorio.getContent().stream()
//                    .map(this::converterParaAvaliacaoDTO)
//                    .toList();
//
//            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
//                    paginaDoRepositorio.getTotalPages(),
//                    page,
//                    size,
//                    avaliacoesDTO
//            );
//        }
//        List<AvaliacaoDTO> listaVazia = new ArrayList<>();
//        return new PageDTO<>(0L, 0, 0, size, listaVazia);
//    }

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

    private AvaliacaoDTO converterEmDTO(AvaliacaoEntity avaliacao) {
        return new AvaliacaoDTO(avaliacao.getIdAvaliacao(),
                avaliacao.getDescricao(),
                avaliacao.getTipoAvaliacao(),
                avaliacao.getDataCriacao(),
                acompanhamentoService.converterEmDTO(avaliacao.getAcompanhamentoEntity()),
                alunoService.converterAlunoDTO(avaliacao.getAlunoEntity()));
    }

    private AvaliacaoEntity converterEmEntity(AvaliacaoCreateDTO avaliacaoCreateDTO,
                                              AcompanhamentoEntity acompanhamento,
                                              AlunoEntity aluno) {
        return new AvaliacaoEntity(null,
                avaliacaoCreateDTO.getIdAluno(),
                avaliacaoCreateDTO.getTipoAvaliacao(),
                avaliacaoCreateDTO.getDescricao(),
                avaliacaoCreateDTO.getDataCriacao(),
                Ativo.S,
                acompanhamento,
                aluno);
    }
}
