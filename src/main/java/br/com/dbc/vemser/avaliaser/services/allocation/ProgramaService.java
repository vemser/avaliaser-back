package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaEdicaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoVagaPrograma;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.ProgramaRepository;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.ModuloRepository;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramaService {

    private final ProgramaRepository programaRepository;
    private final TrilhaRepository trilhaRepository;
    private final ModuloRepository moduloRepository;
    private final ObjectMapper objectMapper;


    public ProgramaDTO create(ProgramaCreateDTO programaCreate) throws RegraDeNegocioException {
        verificarDatas(programaCreate);

        ProgramaEntity programaEntity = objectMapper.convertValue(programaCreate, ProgramaEntity.class);
        programaEntity.setSituacaoVagaPrograma(programaCreate.getSituacaoVagaPrograma());
        programaEntity.setAtivo(Ativo.S);
        ProgramaEntity programaSalvo = programaRepository.save(programaEntity);
        return objectMapper.convertValue(programaSalvo, ProgramaDTO.class);

    }

    public PageDTO<ProgramaDTO> listar(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<ProgramaEntity> programasAbertos = programaRepository.findAllByAtivo(Ativo.S, pageRequest);

            List<ProgramaDTO> clientePagina = programasAbertos.getContent().stream()
                    .map(x -> objectMapper.convertValue(x, ProgramaDTO.class))
                    .toList();
            return new PageDTO<>(programasAbertos.getTotalElements(), programasAbertos.getTotalPages(), pagina, tamanho, clientePagina);
        }
        List<ProgramaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

    }

    public PageDTO<ProgramaDTO> listarPorNome(Integer pagina, Integer tamanho, String nome) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<ProgramaEntity> paginaRepository = programaRepository
                    .findAllByNomeContainingIgnoreCaseAndAtivo(nome.trim().replaceAll("\\s+", " "),Ativo.S, pageRequest);

            List<ProgramaDTO> clientePagina = paginaRepository.getContent().stream()
                    .map(x -> objectMapper.convertValue(x, ProgramaDTO.class))
                    .toList();

            return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, clientePagina);
        }
        List<ProgramaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }


    public ProgramaDTO clonarPrograma(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findById(idPrograma);
        ProgramaEntity programaEntityClone = new ProgramaEntity();
        programaEntityClone.setNome(programaEntity.getNome() + " - Clone");
        programaEntityClone.setDescricao(programaEntity.getDescricao());
        programaEntityClone.setDataInicio(programaEntity.getDataInicio());
        programaEntityClone.setDataFim(programaEntity.getDataFim());
        programaEntityClone.setSituacaoVagaPrograma(programaEntity.getSituacaoVagaPrograma());
        programaEntityClone.setAtivo(programaEntity.getAtivo());
        ProgramaEntity programaEntityCloneSaved = programaRepository.save(programaEntityClone);
        Set<ProgramaEntity> programaEntities = new HashSet<>();
        programaEntities.add(programaEntityCloneSaved);
        Set<TrilhaEntity> trilhaEntities = programaEntity.getTrilhas().stream()
                .map(trilhaEntity -> {
                    TrilhaEntity trilhaEntityClone = new TrilhaEntity();
                    trilhaEntityClone.setPrograma(programaEntities);
                    trilhaEntityClone.setNome(trilhaEntity.getNome());
                    trilhaEntityClone.setDescricao(trilhaEntity.getDescricao());
                    trilhaEntityClone.setAtivo(trilhaEntity.getAtivo());
                    TrilhaEntity trilhaEntityCloneSaved = trilhaRepository.save(trilhaEntityClone);
                    Set<ModuloEntity> mouloEntities = trilhaEntity.getModulos().stream()
                            .map(moduloEntity -> {
                                ModuloEntity ModuloEntityClone = new ModuloEntity();
                                Set<TrilhaEntity> trilhaEntities1 = new HashSet<>(moduloEntity.getTrilha());
                                Set<FeedBackEntity> feedBackEntities = new HashSet<>(moduloEntity.getFeedBack());
                                ModuloEntityClone.setNome(moduloEntity.getNome());
                                ModuloEntityClone.setAtivo(moduloEntity.getAtivo());
                                ModuloEntityClone.setTrilha(trilhaEntities1);
                                ModuloEntityClone.setFeedBack(feedBackEntities);
                                return moduloRepository.save(ModuloEntityClone);
                            }).collect(Collectors.toSet());
                    trilhaEntityClone.setModulos(mouloEntities);
                    return trilhaEntityCloneSaved;
                }).collect(Collectors.toSet());
        programaEntityClone.setTrilhas(trilhaEntities);
        return objectMapper.convertValue(programaEntityCloneSaved, ProgramaDTO.class);
    }

    public ProgramaDTO buscarProgramaPorId(Integer idPrograma) throws RegraDeNegocioException {

        ProgramaEntity programaEntity = programaRepository.findByIdProgramaAndAtivo(idPrograma,Ativo.S)
                .orElseThrow(()-> new RegraDeNegocioException("Não foi possivel localizar este programa!"));
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }


    public List<ProgramaEntity> findAllById(List<Integer> ids) throws RegraDeNegocioException {
        for (int i = 0; i < ids.size(); i++) {
            ProgramaEntity programaEntity = findByIdAtivo(ids.get(i));
            verificarProgramaFechado(programaEntity);
        }
        return programaRepository.findAllById(ids);
    }



    public ProgramaDTO editar(Integer idPrograma, ProgramaEdicaoDTO programaEdicao) throws RegraDeNegocioException {
        verificarDatasEdicao(programaEdicao);

        ProgramaEntity programaEntity = findByIdAtivo(idPrograma);
        programaEntity.setNome(programaEdicao.getNome());
        programaEntity.setDescricao(programaEdicao.getDescricao());
        programaEntity.setDataInicio(programaEdicao.getDataInicio());
        if (!programaEntity.getDataInicio().equals(programaEdicao.getDataInicio())) {
            programaEntity.setDataInicio(programaEdicao.getDataInicio());
        }
        programaEntity.setSituacaoVagaPrograma(programaEdicao.getSituacaoVagaPrograma());
        programaEntity.setDataFim(programaEdicao.getDataFim());

        ProgramaEntity programaSalvo = programaRepository.save(programaEntity);
        return objectMapper.convertValue(programaSalvo, ProgramaDTO.class);
    }

    public void desativar(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findByIdAtivo(idPrograma);
        programaEntity.setAtivo(Ativo.N);
        programaRepository.save(programaEntity);
    }

    public ProgramaEntity findByIdAtivo(Integer id) throws RegraDeNegocioException {
        return programaRepository.findByIdProgramaAndAtivo(id, Ativo.S)
                .orElseThrow(() -> new RegraDeNegocioException("Programa não encontrado"));
    }
    public ProgramaEntity findById(Integer id) throws RegraDeNegocioException {
        return programaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Programa não encontrado"));
    }


    public ProgramaDTO converterEmDTO(ProgramaEntity programaEntity) {
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }

    public void verificarProgramaFechado(ProgramaEntity programaEntity) throws RegraDeNegocioException {
        if (programaEntity.getSituacaoVagaPrograma().equals(SituacaoVagaPrograma.valueOf("FECHADO"))) {
            throw new RegraDeNegocioException("Programa de id " + programaEntity.getIdPrograma() + " Fechado!");
        }
    }

    private static void verificarDatas(ProgramaCreateDTO programaCreate) throws RegraDeNegocioException {
        if(programaCreate.getDataFim().isBefore(programaCreate.getDataInicio())) {
            throw new RegraDeNegocioException("A data final do programa não pode ser inferior a data inicial. Tente novamente!");
        }
    }
    private static void verificarDatasEdicao(ProgramaEdicaoDTO programaEdicaoDTO) throws RegraDeNegocioException {
        if(programaEdicaoDTO.getDataFim().isBefore(programaEdicaoDTO.getDataInicio())) {
            throw new RegraDeNegocioException("A data final do programa não pode ser inferior a data inicial. Tente novamente!");
        }
    }

    public ProgramaEntity findByIdPrograma(Integer idPrograma) {
        Optional<ProgramaEntity> programa = programaRepository.findByIdProgramaAndAtivo(idPrograma, Ativo.S);
        return programa.orElse(null);
    }

    public ProgramaDTO fecharPrograma(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaEntity programa = findByIdAtivo(idPrograma);
        programa.setSituacaoVagaPrograma(SituacaoVagaPrograma.FECHADO);
        ProgramaEntity programaEntity = programaRepository.save(programa);
        return converterEmDTO(programaEntity);
    }
}
