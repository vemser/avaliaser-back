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

import java.util.*;
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

    public TrilhaDTO pegarIdTrilha(Integer id) throws RegraDeNegocioException {
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
    public List<TrilhaDTO> findTrilhaByNome(String nomeTrilha) {
        if (nomeTrilha != null) {
            return trilhaRepository.findAllByNomeContainingIgnoreCase(nomeTrilha.trim().replaceAll("\\s+", " "))
                    .stream()
                    .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
                    .toList();
        }
        return trilhaRepository.findAll().stream()
                .map(trilhaEntity -> objectMapper.convertValue(trilhaEntity, TrilhaDTO.class))
                .toList();
    }


    public PageDTO<TrilhaDTO> listarAllTrilhaPaginado(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<TrilhaEntity> trilha = trilhaRepository.findAll(pageRequest);

            List<TrilhaDTO> trilhaDTOList = trilha.getContent().stream()
                    .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, TrilhaDTO.class))
                    .toList();
            return new PageDTO<>(trilha.getTotalElements(),
                    trilha.getTotalPages(),
                    pagina,
                    tamanho,
                    trilhaDTOList
            );
        }
        List<TrilhaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public void desativar(Integer idTrilha) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(idTrilha);
        trilhaEntity.setAtivo(Ativo.valueOf("N"));
        trilhaRepository.save(trilhaEntity);
    }

    public List<RankingDTO> rankingtrilha(Integer idTrilha) throws RegraDeNegocioException {
        TrilhaEntity trilha = findById(idTrilha);
        List<RankingDTO> listAlunos = trilha.getAlunos()
                .stream()
                .sorted(Comparator.comparing(AlunoEntity::getPontuacao)
                        .reversed())
                .limit(5L)
                .map(this::mapRankingDTO)
                .collect(Collectors
                        .toList());
        return listAlunos;
    }

    private RankingDTO mapRankingDTO(AlunoEntity alunoEntity) {
        return new RankingDTO(alunoEntity.getNome(), alunoEntity.getPontuacao());
    }
    public TrilhaDTO converterEmDTO(TrilhaEntity trilhaEntity) {
        return objectMapper.convertValue(trilhaEntity,TrilhaDTO.class);
    }
    public void verificarTrilhaDesativada(TrilhaEntity trilhaEntity) throws RegraDeNegocioException {
        if (trilhaEntity.getAtivo().equals(Ativo.valueOf("N"))){
            throw new RegraDeNegocioException("Trilha desativada!");
        }
    }

}
