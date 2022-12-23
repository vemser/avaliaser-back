package br.com.dbc.vemser.avaliaser.services.vemrankser;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final ModuloService moduloService;
    private final UsuarioService usuarioService;
    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;

    private static final Integer INICIAL_PONTUACAO = 0;


    public AtividadePaginacaoDTO<AtividadeDTO> listarAtividades(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);

        Page<AtividadeEntity> atividadeEntity = atividadeRepository.findAll(pageRequest);
        List<AtividadeDTO> atividadeDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeDTO atividadeDTO = objectMapper.convertValue(atividade, AtividadeDTO.class);
                    return atividadeDTO;
                })
                .toList();
        if (atividadeDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Não foi encontrada nenhuma atividade");
        }

        return new AtividadePaginacaoDTO<>(atividadeEntity.getTotalElements(), atividadeEntity.getTotalPages(), pagina, tamanho, atividadeDTOList);
    }

    public AtividadeDTO createAtividade(AtividadeCreateDTO atividadeCreateDTO, Integer idModulo, List<Integer> idTrilha) throws RegraDeNegocioException {
        UsuarioLogadoDTO loggedUser = usuarioService.getLoggedUser();
        ModuloEntity moduloEntity = moduloService.buscarPorIdModulo(idModulo);
        List<TrilhaEntity> trilhaEntities = new ArrayList<>();
        AtividadeEntity atividadeEntity = objectMapper.convertValue(atividadeCreateDTO, AtividadeEntity.class);
        atividadeEntity.setNomeInstrutor(loggedUser.getNome());
        atividadeEntity.setPontuacao(INICIAL_PONTUACAO);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        atividadeEntity.setDataCriacao(now);

        for (Integer number : idTrilha) {
            TrilhaEntity trilhaEntity = trilhaService.findById(number);
            atividadeEntity.getAlunos().addAll(trilhaEntity.getUsuarios());
            trilhaEntities.add(trilhaEntity);

        }
        atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE);
        atividadeEntity.setTrilhas(new HashSet<>(trilhaEntities));
        atividadeEntity.setModulo(moduloEntity);

        atividadeRepository.save(atividadeEntity);

        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);


    }

    public AtividadeDTO findById(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);

    }

    public AtividadeDTO colocarAtividadeComoConcluida(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        atividadeEntity.setStatusAtividade(AtividadeStatus.CONCLUIDA);
        atividadeRepository.save(atividadeEntity);

        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);

    }

    public PageDTO<AtividadeTrilhaDTO> listarAtividadePorStatus(Integer pagina, Integer tamanho, Integer idTrilha, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeTrilhaDTO> atividadeTrilha = atividadeRepository.listarAtividadePorStatus(pageRequest, idTrilha, atividadeStatus);

        List<AtividadeTrilhaDTO> atividadeTrilhaDTOS = atividadeTrilha.getContent()
                .stream()
                .map(atividadeTrilhaDTO -> {
                    objectMapper.convertValue(atividadeTrilhaDTO, AtividadeTrilhaDTO.class);
                    return atividadeTrilhaDTO;
                })
                .toList();
        if (atividadeTrilhaDTOS.isEmpty()) {
            throw new RegraDeNegocioException("Atividade não cadastrada");
        }

        return new PageDTO<>(atividadeTrilha.getTotalElements(),
                atividadeTrilha.getTotalPages(),
                pagina,
                tamanho,
                atividadeTrilhaDTOS);
    }

    public PageDTO<AtividadeMuralDTO> listarAtividadeMuralInstrutor(Integer pagina, Integer tamanho, Integer idTrilha) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeMuralDTO> atividadeEntity = atividadeRepository.listarAtividadeMuralInstrutor(pageRequest, idTrilha);

        List<AtividadeMuralDTO> atividadeMuralDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> objectMapper.convertValue(atividade, AtividadeMuralDTO.class))
                .toList();

        if (atividadeMuralDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Sem atividades no mural do instrutor");
        }

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeMuralDTOList);
    }

    public PageDTO<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer pagina, Integer tamanho, Integer idAluno, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeMuralAlunoDTO> atividadeEntity = atividadeRepository.listarAtividadeMuralAluno(idAluno, atividadeStatus, pageRequest);

        List<AtividadeMuralAlunoDTO> atividadeMuralDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> objectMapper.convertValue(atividade, AtividadeMuralAlunoDTO.class))
                .toList();

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeMuralDTOList);
    }

    public PageDTO<AtividadeNotaPageDTO> listarAtividadePorIdTrilhaIdModulo(Integer pagina, Integer tamanho, Integer idTrilha, Integer idModulo, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeNotaPageDTO> atividadeEntity = atividadeRepository.listarAtividadePorIdTrilhaIdModulo(pageRequest, idTrilha, idModulo, atividadeStatus);

        List<AtividadeNotaPageDTO> atividadeNotaDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeNotaPageDTO atividadeNotaDTO1 = objectMapper.convertValue(atividade, AtividadeNotaPageDTO.class);
                    return atividadeNotaDTO1;
                })
                .toList();

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeNotaDTOList);
    }

    public AtividadeEntity buscarPorIdAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return atividadeRepository.findById(idAtividade)
                .orElseThrow(() -> new RegraDeNegocioException("Atividade não encontrada."));
    }

    public AtividadeDTO save(AtividadeEntity atividadeEntity) {
        return objectMapper.convertValue(atividadeRepository.save(atividadeEntity), AtividadeDTO.class);
    }

}
