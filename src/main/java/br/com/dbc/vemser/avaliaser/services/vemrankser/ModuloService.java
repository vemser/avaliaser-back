package br.com.dbc.vemser.avaliaser.services.vemrankser;


import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.ModuloRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;

    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;


    public ModuloDTO criar(ModuloCreateDTO modulo) {
        ModuloEntity moduloEntityNovo = converterEntity(modulo);
        moduloEntityNovo.setAtivo(Ativo.S);
        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntityNovo);
        return converterEmDTO(moduloSalvo);

    }
    public ModuloDTO editar(Integer id,ModuloCreateDTO moduloCreateDTO) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(id);
        moduloEntity.setNome(moduloCreateDTO.getNome());
        moduloEntity.setDataInicio(moduloCreateDTO.getDataInicio());
        moduloEntity.setDataFim(moduloCreateDTO.getDataFim());
        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntity);
        return converterEmDTO(moduloSalvo);
    }
    public void desativar(Integer id) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(id);
        moduloEntity.setAtivo(Ativo.N);
        moduloRepository.save(moduloEntity);
    }

    private ModuloEntity converterEntity(ModuloCreateDTO modulo) {
        return objectMapper.convertValue(modulo, ModuloEntity.class);
    }

    public ModuloEntity buscarPorIdModulo(Integer idModulo) throws RegraDeNegocioException {
        return moduloRepository.findById(idModulo)
                .orElseThrow(() -> new RegraDeNegocioException("Modulo não encontrado."));
    }

    public ModuloDTO findById(Integer idModulo) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(idModulo);
        return converterEmDTO(moduloEntity);

    }

    private ModuloDTO converterEmDTO(ModuloEntity moduloEntity) {
        return objectMapper.convertValue(moduloEntity, ModuloDTO.class);
    }

    public PageDTO<ModuloDTO> listarModulo(Integer page,Integer size) {
      List<ModuloDTO> moduloDTOS = moduloRepository.findAll()
                .stream()
                .map(modulo -> converterEmDTO(modulo))
                .toList();
        Page<ModuloDTO> pagina = new PageImpl<>(moduloDTOS);

        return new PageDTO<>(pagina.getTotalElements(),
                pagina.getTotalPages(),
                page,
                size,
                moduloDTOS);
    }


    public ModuloDTO vincularModuloTrilha(Integer idModulo,
                                          Integer idTrilha) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(idModulo);
        TrilhaEntity trilhaEntity = trilhaService.findById(idTrilha);
        moduloEntity.getTrilhas().add(trilhaEntity);
        moduloRepository.save(moduloEntity);
        return converterEmDTO(moduloEntity);
    }

    public List<ModuloDTO> listAllModulos() {
        return moduloRepository.findAll().stream()
                .map(this::converterEmDTO)
                .toList();
    }

    public ModuloDTO clonarModulo(Integer idModulo) {
        ModuloEntity modulo = moduloRepository.findById(idModulo).get();
        ModuloEntity moduloEntity = new ModuloEntity(null,
                modulo.getNome(),
                modulo.getDataInicio(),
                modulo.getDataFim(),
                modulo.getAtivo(),
                new HashSet<>(modulo.getTrilhas()),
                new HashSet<>(modulo.getProgramas()));

        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntity);
        return new ModuloDTO(moduloSalvo.getIdModulo(),
                moduloSalvo.getNome(),
                moduloSalvo.getDataInicio(),
                moduloSalvo.getDataFim(),
                moduloSalvo.getAtivo());
    }


}
