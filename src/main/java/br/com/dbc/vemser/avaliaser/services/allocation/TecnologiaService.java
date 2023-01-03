package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.TecnologiaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public PageDTO<TecnologiaDTO> buscarPorTecnologia(String nome,Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<TecnologiaEntity> paginaRepository =
                    tecnologiaRepository.findByNomeContainingIgnoreCase(nome, pageRequest);

            List<TecnologiaDTO> tecnologiaDTOS = getTecnologiaDTOS(paginaRepository);

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    page,
                    size,
                    tecnologiaDTOS);
        }
        List<TecnologiaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    @NotNull
    private List<TecnologiaDTO> getTecnologiaDTOS(Page<TecnologiaEntity> paginaRepository) {
        List<TecnologiaDTO> tecnologiaDTO = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .toList();
        return tecnologiaDTO;
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

    public TecnologiaEntity findByIdTecnologia(Integer idTecnologia) throws RegraDeNegocioException {
        return tecnologiaRepository.findById(idTecnologia).orElseThrow(() -> new RegraDeNegocioException("Tecnologia não encontrado."));
    }

    public TecnologiaDTO findByIdDTO (Integer id) throws RegraDeNegocioException{
        TecnologiaEntity tecnologia = findByIdTecnologia(id);
        return converterEmDTO(tecnologia);
    }
}