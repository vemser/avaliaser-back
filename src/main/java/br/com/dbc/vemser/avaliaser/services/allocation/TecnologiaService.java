package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.TecnologiaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TecnologiaService {
    private final TecnologiaRepository tecnologiaRepository;
    private final ObjectMapper objectMapper;

    public TecnologiaDTO create(TecnologiaCreateDTO tecnologiaCreate) {
        TecnologiaEntity tecnologiaEntity = converteEmEntity(tecnologiaCreate);
        tecnologiaRepository.save(tecnologiaEntity);
        return converterEmDTO(tecnologiaEntity);
    }

    public PageDTO<TecnologiaDTO> buscarPorTecnologia(String nomeTecnologia, PageRequest pageRequest) {

        Page<TecnologiaEntity> tecnologiaEntities = tecnologiaRepository.findByNomeIsLikeIgnoreCase(nomeTecnologia, pageRequest);
        List<TecnologiaDTO> tecnologiaDTOS = tecnologiaEntities.getContent().stream().map(this::converterEmDTO).collect(Collectors.toList());

        return new PageDTO<>(tecnologiaEntities.getTotalElements(),
                tecnologiaEntities.getTotalPages(),
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                tecnologiaDTOS
        );
    }

    public TecnologiaDTO findByName(String nome) {
        return converterEmDTO(tecnologiaRepository.findByNome(nome));
    }

    public Set<TecnologiaEntity> findBySet(List<String> tecnologias) {
        return tecnologiaRepository.findAllByNomeIn(tecnologias);
    }

    public TecnologiaEntity converteEmEntity(TecnologiaCreateDTO tecnologiaCreateDTO) {
        return objectMapper.convertValue(tecnologiaCreateDTO, TecnologiaEntity.class);
    }

    public TecnologiaDTO converterEmDTO(TecnologiaEntity tecnologiaEntity) {
        return objectMapper.convertValue(tecnologiaEntity, TecnologiaDTO.class);
    }

    public TecnologiaEntity findByIdTecnologia(Integer idTecnologia) {
        return objectMapper.convertValue(tecnologiaRepository.findByIdTecnologia(idTecnologia), TecnologiaEntity.class);
    }
}