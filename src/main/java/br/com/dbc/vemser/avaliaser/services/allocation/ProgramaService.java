package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
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

        ProgramaEntity programaEntity = objectMapper.convertValue(programaCreate, ProgramaEntity.class);
        if(programaCreate.getDataFim().isBefore(programaCreate.getDataInicio())) {
            throw new RegraDeNegocioException("A data final do programa não pode ser inferior a data inicial. Tente novamente!");
        }
        programaEntity.setSituacao(Situacao.valueOf(programaCreate.getSituacao()));

        return objectMapper.convertValue(programaRepository.save(programaEntity), ProgramaDTO.class);
    }

    public PageDTO<ProgramaDTO> listar(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size não poder ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<ProgramaEntity> programasAbertos = programaRepository.findAllProgramasAbertos(pageRequest);

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
            Page<ProgramaEntity> paginaRepository = programaRepository.findAllByNomeContainingIgnoreCase(nome, pageRequest);

            List<ProgramaDTO> clientePagina = paginaRepository.getContent().stream()
                    .map(x -> objectMapper.convertValue(x, ProgramaDTO.class))
                    .toList();

            return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, clientePagina);
        }
        List<ProgramaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

//    public PageDTO<ProgramaDTO> listarPorId(Integer idPrograma) throws RegraDeNegocioException {
//        List<ProgramaDTO> list = List.of(objectMapper.convertValue(buscarProgramaId(idPrograma), ProgramaDTO.class));
//        Page<ProgramaDTO> page = new PageImpl<>(list);
//
//        return new PageDTO<>(page.getTotalElements(),
//                page.getTotalPages(),
//                0,
//                1,
//                list
//        );
//    }

    public ProgramaDTO pegarPrograma(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findById(idPrograma);
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }


    public List<ProgramaEntity> findAllById(List<Integer> ids) throws RegraDeNegocioException {
        for (int i = 0; i < ids.size(); i++) {
            ProgramaEntity programaEntity = findById(ids.get(i));
            verificarProgramaFechado(programaEntity);
        }
        return programaRepository.findAllById(ids);
    }

    public ProgramaDTO editar(Integer idPrograma, ProgramaCreateDTO programaCreate) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findById(idPrograma);
        programaEntity.setSituacao(Situacao.valueOf(programaCreate.getSituacao()));
        programaEntity.setNome(programaCreate.getNome());
        programaEntity.setDescricao(programaCreate.getDescricao());
        programaEntity.setDataInicio(programaCreate.getDataInicio());
        programaEntity.setDataFim(programaCreate.getDataFim());

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


}
