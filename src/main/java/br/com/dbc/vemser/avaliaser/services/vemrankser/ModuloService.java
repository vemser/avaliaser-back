package br.com.dbc.vemser.avaliaser.services.vemrankser;


import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.ModuloRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;

    private final TrilhaService trilhaService;

    private final ProgramaService programaService;
    private final ObjectMapper objectMapper;

    public PageDTO<ModuloDTO> listByName(Integer idModulo, String nome, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            Page<ModuloEntity> paginaDoRepositorio = filtrarModulos(idModulo, nome, pagina, tamanho);
            List<ModuloDTO> moduloDTOS = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    moduloDTOS);
        }
        List<ModuloDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public ModuloDTO criar(ModuloCreateDTO modulo) throws RegraDeNegocioException {
        ModuloEntity moduloEntityNovo = converterEntity(modulo);

        moduloEntityNovo.setAtivo(Ativo.valueOf("S"));
        if (modulo.getListPrograma().size() > 0) {
            for (Integer programa : modulo.getListPrograma()) {
                ProgramaEntity programaEntity = programaService.findByIdPrograma(programa);
                if (!(programaEntity == null)) {
                    moduloEntityNovo.getProgramas().add(programaEntity);
                }
            }
        }
        if (modulo.getTrilha().size() > 0) {
            for (Integer trilha : modulo.getTrilha()) {
                TrilhaEntity trilhaEnt = trilhaService.findByIdTrilha(trilha);
                if (!(trilhaEnt == null)) {
                    moduloEntityNovo.getTrilha().add(trilhaEnt);
                }
            }
        }
        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntityNovo);
        return converterEmDTO(moduloSalvo);
    }

    public ModuloDTO editar(Integer id, ModuloCreateDTO moduloCreateDTO) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(id);
        moduloEntity.setNome(moduloCreateDTO.getNome());
        if (moduloCreateDTO.getListPrograma().size() > 0) {
            moduloEntity.getProgramas().clear();
            for (Integer programa : moduloCreateDTO.getListPrograma()) {
                ProgramaEntity programaEntity = programaService.findByIdPrograma(programa);
                if (!(programaEntity == null)) {
                    moduloEntity.getProgramas().add(programaEntity);
                }
            }
        }
        if (moduloCreateDTO.getTrilha().size() > 0) {
            moduloEntity.getTrilha().clear();
            for (Integer trilha : moduloCreateDTO.getTrilha()) {
                TrilhaEntity trilhaEnt = trilhaService.findByIdTrilha(trilha);
                if (!(trilhaEnt == null)) {
                    moduloEntity.getTrilha().add(trilhaEnt);
                }
            }
        }
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
        return moduloRepository.findByIdModuloAndAtivo(idModulo, Ativo.S)
                .orElseThrow(() -> new RegraDeNegocioException("Modulo não encontrado."));
    }

    public ModuloDTO findById(Integer idModulo) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(idModulo);
        return converterEmDTO(moduloEntity);

    }

    public ModuloEntity findModuloEntityById(Integer idModulo) {
        return moduloRepository.findById(idModulo).get();
    }

    public ModuloDTO converterEmDTO(ModuloEntity moduloEntity) {

        ModuloDTO moduloDTO = objectMapper.convertValue(moduloEntity, ModuloDTO.class);
        moduloDTO.setIdModulo(moduloEntity.getIdModulo());
        moduloDTO.setNome(moduloEntity.getNome());
        moduloDTO.setAtivo(moduloEntity.getAtivo());
        moduloDTO.setListProgramaDTO(moduloEntity.getProgramas().stream()
                .map(programaService::converterEmDTO)
                .collect(Collectors.toList()));
        moduloDTO.setTrilhaDTO(moduloEntity.getTrilha().stream()
                .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
                .toList());
        return moduloDTO;
    }


    public PageDTO<ModuloDTO> listarModulo(Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ModuloEntity> paginaDoRepositorio = moduloRepository.findAllByAtivo(Ativo.S, pageRequest);
            List<ModuloDTO> moduloPaginas = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    page,
                    size,
                    moduloPaginas);
        }
        List<ModuloDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }


    public ModuloDTO vincularModuloTrilha(Integer idModulo,
                                          Integer idTrilha) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(idModulo);
        TrilhaEntity trilhaEntity = trilhaService.findById(idTrilha);
        moduloEntity.getTrilha().add(trilhaEntity);
        moduloRepository.save(moduloEntity);
        return converterEmDTO(moduloEntity);
    }

    public List<ModuloDTO> listAllModulos() {
        return moduloRepository.findAll().stream()
                .map(this::converterEmDTO)
                .toList();
    }

    public ModuloDTO clonarModulo(Integer idModulo) throws RegraDeNegocioException {
        ModuloEntity modulo = buscarPorIdModulo(idModulo);
        ModuloEntity moduloEntity = new ModuloEntity(null,
                modulo.getNome(),
                modulo.getAtivo(),
                new HashSet<>(modulo.getTrilha()),
                new HashSet<>(modulo.getProgramas()),
                new HashSet<>(modulo.getFeedBack()));
        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntity);
        return converterEmDTO(moduloSalvo);
    }

    private Page<ModuloEntity> filtrarModulos(Integer idModulo, String nome, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idModulo == null)) {
            return moduloRepository.findAllByIdModuloAndAtivo(pageRequest, idModulo, Ativo.S);
        } else if (!(nome == null)) {
            return moduloRepository.findAllByNomeContainingIgnoreCaseAndAtivo(pageRequest, nome, Ativo.S);
        }
        return moduloRepository.findAllByAtivo(Ativo.S, pageRequest);
    }


}
