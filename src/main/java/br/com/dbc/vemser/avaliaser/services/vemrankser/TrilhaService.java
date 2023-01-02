package br.com.dbc.vemser.avaliaser.services.vemrankser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.rankdto.RankingDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;
    private final ObjectMapper objectMapper;


    public TrilhaDTO create(TrilhaCreateDTO trilhaNova) {
        TrilhaEntity trilha = objectMapper.convertValue(trilhaNova, TrilhaEntity.class);
        trilha.setAtivo(Ativo.valueOf("S"));
        trilhaRepository.save(trilha);
        return objectMapper.convertValue(trilha, TrilhaDTO.class);
    }

    public TrilhaDTO updateTrilha(Integer idTrilha, TrilhaCreateDTO trilhaAtualizar) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(idTrilha);
        trilhaEntity.setNome(trilhaAtualizar.getNome());
        trilhaEntity.setDescricao(trilhaAtualizar.getDescricao());
        return objectMapper.convertValue(trilhaRepository.save(trilhaEntity), TrilhaDTO.class);
    }

    public TrilhaDTO getIdTrilha(Integer id) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(id);
        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
    }

    public TrilhaEntity findById(Integer idTrilha) throws RegraDeNegocioException {
        return trilhaRepository.findById(idTrilha)
                .orElseThrow(() -> new RegraDeNegocioException("Trilha não encontrada."));
    }

    public List<TrilhaEntity> findAllById(List<Integer> ids) {
        return trilhaRepository.findAllById(ids);

    }

    public PageDTO<TrilhaDTO> findTrilhaByNome(String nomeTrilha, PageRequest pageRequest) throws RegraDeNegocioException {
        if (pageRequest.getPageNumber() < 0 || pageRequest.getPageSize() < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (pageRequest.getPageSize() > 0) {
            Page<TrilhaEntity> trilhaEntities = trilhaRepository
                    .findAllByNomeContainingIgnoreCase(nomeTrilha.trim().replaceAll("\\s+", " "), pageRequest);

            List<TrilhaDTO> trilhaDTOS = trilhaEntities.getContent().stream().map(trilhaEntity -> objectMapper.convertValue(trilhaEntity, TrilhaDTO.class))
                    .collect(Collectors.toList());

            return new PageDTO<>(trilhaEntities.getTotalElements(),
                    trilhaEntities.getTotalPages(),
                    pageRequest.getPageNumber(),
                    pageRequest.getPageSize(),
                    trilhaDTOS);
        }

        List<TrilhaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, pageRequest.getPageSize(), listaVazia);
    }


    public PageDTO<TrilhaDTO> listarAllTrilhaPaginado(Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<TrilhaEntity> trilha = trilhaRepository.findAll(pageRequest);

            List<TrilhaDTO> trilhaDTOList = trilha.getContent().stream()
                    .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, TrilhaDTO.class))
                    .toList();
            return new PageDTO<>(trilha.getTotalElements(),
                    trilha.getTotalPages(),
                    page,
                    size,
                    trilhaDTOList
            );
        }
        List<TrilhaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public void desativar(Integer idTrilha) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(idTrilha);
        trilhaEntity.setAtivo(Ativo.valueOf("N"));
        trilhaRepository.save(trilhaEntity);
    }

    public PageDTO<RankingDTO> rankingTrilha(Integer page, Integer size,Integer idTrilha) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<TrilhaEntity> trilhaEntityPage = trilhaRepository.findByIdTrilha(idTrilha,pageRequest);
            TrilhaEntity trilha = findById(idTrilha);
            List<RankingDTO> listRankingAlunos = trilha.getAlunos()
                    .stream()
                    .sorted(Comparator.comparing(AlunoEntity::getPontuacao)
                            .reversed())
                    .limit(5L)
                    .map(this::mapRankingDTO)
                    .collect(Collectors
                            .toList());
            return new PageDTO<>(trilhaEntityPage.getTotalElements(),
                    trilhaEntityPage.getTotalPages(),
                    page,
                    size,
                    listRankingAlunos);
        }
        List<RankingDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    private RankingDTO mapRankingDTO(AlunoEntity alunoEntity) {
        return new RankingDTO(alunoEntity.getNome(), alunoEntity.getPontuacao());
    }

    public TrilhaDTO converterEmDTO(TrilhaEntity trilhaEntity) {
        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
    }

    public void verificarTrilhaDesativada(TrilhaEntity trilhaEntity) throws RegraDeNegocioException {
        if (trilhaEntity.getAtivo().equals(Ativo.valueOf("N"))) {
            throw new RegraDeNegocioException("Trilha desativada!");
        }
    }


}
