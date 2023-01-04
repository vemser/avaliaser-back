package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaEdicaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.ProgramaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramaService {

    private final ProgramaRepository programaRepository;
    private final ObjectMapper objectMapper;


    public ProgramaDTO create(ProgramaCreateDTO programaCreate) throws RegraDeNegocioException {
        verificarDatas(programaCreate);

        ProgramaEntity programaEntity = objectMapper.convertValue(programaCreate, ProgramaEntity.class);
        programaEntity.setSituacao(Situacao.valueOf(programaCreate.getSituacao()));

        return objectMapper.convertValue(programaRepository.save(programaEntity), ProgramaDTO.class);
    }

    public PageDTO<ProgramaDTO> listar(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<ProgramaEntity> programasAbertos = programaRepository.findAll(pageRequest);

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
                    .findAllByNomeContainingIgnoreCase(nome.trim().replaceAll("\\s+", " "), pageRequest);

            List<ProgramaDTO> clientePagina = paginaRepository.getContent().stream()
                    .map(x -> objectMapper.convertValue(x, ProgramaDTO.class))
                    .toList();

            return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, clientePagina);
        }
        List<ProgramaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }


    public ProgramaDTO buscarProgramaPorId(Integer idPrograma) throws RegraDeNegocioException {

        ProgramaEntity programaEntity = programaRepository.findById(idPrograma)
                .orElseThrow(()-> new RegraDeNegocioException("Não foi possivel localizar este programa!"));
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }


    public List<ProgramaEntity> findAllById(List<Integer> ids) throws RegraDeNegocioException {
        for (int i = 0; i < ids.size(); i++) {
            ProgramaEntity programaEntity = findById(ids.get(i));
            verificarProgramaFechado(programaEntity);
        }
        return programaRepository.findAllById(ids);
    }



    public ProgramaDTO editar(Integer idPrograma, ProgramaEdicaoDTO programaEdicao) throws RegraDeNegocioException {
        verificarDatasEdicao(programaEdicao);

        ProgramaEntity programaEntity = findById(idPrograma);
        programaEntity.setNome(programaEdicao.getNome());
        programaEntity.setDescricao(programaEdicao.getDescricao());
        programaEntity.setDataInicio(programaEdicao.getDataInicio());
        if (!programaEntity.getDataInicio().equals(programaEdicao.getDataInicio())) {
            programaEntity.setDataInicio(programaEdicao.getDataInicio());
        }
        programaEntity.setDataFim(programaEdicao.getDataFim());

        programaRepository.save(programaEntity);
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }

    public void desativar(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findById(idPrograma);
        programaEntity.setSituacao(Situacao.FECHADO);
        programaRepository.save(programaEntity);
    }

    public ProgramaEntity findById(Integer id) throws RegraDeNegocioException {
        return programaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Programa não encontrado"));
    }


    public ProgramaDTO converterEmDTO(ProgramaEntity programaEntity) {
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }

    public void verificarProgramaFechado(ProgramaEntity programaEntity) throws RegraDeNegocioException {
        if (programaEntity.getSituacao().equals(Situacao.valueOf("FECHADO"))) {
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
}
