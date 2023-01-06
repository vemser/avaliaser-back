package br.com.dbc.vemser.avaliaser.services.vemrankser;


import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.entities.*;
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
import java.util.Set;
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
        verificarDatas(modulo);
        ModuloEntity moduloEntityNovo = converterEntity(modulo);
        moduloEntityNovo.setAtivo(Ativo.valueOf("S"));
        TrilhaEntity trilhaEntity = trilhaService.findById(modulo.getIdTrilha());
        trilhaService.verificarTrilhaDesativada(trilhaEntity);
        moduloEntityNovo.setTrilha(trilhaEntity);
        Set<ProgramaEntity> programaEntitySet = new HashSet<>(programaService
                .findAllById(modulo.getListPrograma()));
        moduloEntityNovo.setProgramas(programaEntitySet);
        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntityNovo);

        return converterEmDTO(moduloSalvo);

    }

    public ModuloDTO editar(Integer id, ModuloCreateDTO moduloCreateDTO) throws RegraDeNegocioException {
        verificarDatas(moduloCreateDTO);
        ModuloEntity moduloEntity = buscarPorIdModulo(id);

        moduloEntity.setNome(moduloCreateDTO.getNome());
        moduloEntity.setDataInicio(moduloCreateDTO.getDataInicio());
        moduloEntity.setDataFim(moduloCreateDTO.getDataFim());
        TrilhaEntity trilhaEntity = trilhaService.findById(moduloCreateDTO.getIdTrilha());
        moduloEntity.setTrilha(trilhaEntity);
        Set<ProgramaEntity> programaEntitySet = new HashSet<>(programaService
                .findAllById(moduloCreateDTO.getListPrograma()));
        moduloEntity.setProgramas(programaEntitySet);
        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntity);
        return converterEmDTO(moduloSalvo);
    }

    public void desativar(Integer id) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = buscarPorIdModulo(id);
        moduloEntity.setAtivo(Ativo.valueOf("N"));
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

    private ModuloDTO converterEmDTO(ModuloEntity moduloEntity) {
        return new ModuloDTO(moduloEntity.getIdModulo(),
                moduloEntity.getNome(),
                moduloEntity.getDataInicio(),
                moduloEntity.getDataFim(),
                moduloEntity.getAtivo(),
                trilhaService.converterEmDTO(moduloEntity.getTrilha()),
                moduloEntity.getProgramas().stream().map(programaService::converterEmDTO)
                        .collect(Collectors.toList()));
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
        moduloEntity.setTrilha(trilhaEntity);
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
                modulo.getDataInicio(),
                modulo.getDataFim(),
                modulo.getAtivo(),
                trilhaService.findById(modulo.getTrilha().getIdTrilha()),
                new HashSet<>(modulo.getProgramas()));

        ModuloEntity moduloSalvo = moduloRepository.save(moduloEntity);
        return new ModuloDTO(moduloSalvo.getIdModulo(),
                moduloSalvo.getNome(),
                moduloSalvo.getDataInicio(),
                moduloSalvo.getDataFim(),
                moduloSalvo.getAtivo(),
                trilhaService.converterEmDTO(moduloEntity.getTrilha()),
                modulo.getProgramas().stream().map(programaService::converterEmDTO)
                        .collect(Collectors.toList()));

    }
    private static void verificarDatas(ModuloCreateDTO moduloCreateDTO) throws RegraDeNegocioException {
        if (moduloCreateDTO.getDataInicio().isAfter(moduloCreateDTO.getDataFim())) {
            throw new RegraDeNegocioException("Data de abertura não pode ser maior que a data de fechamento no cadastro.");
        }
    }
    private Page<ModuloEntity> filtrarModulos(Integer idModulo, String nome, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idModulo == null)) {
            return moduloRepository.findAllByIdModulo(pageRequest, idModulo);
        } else if (!(nome == null)) {
            return moduloRepository.findAllByNomeContainingIgnoreCase(pageRequest, nome);
        }
        return moduloRepository.findAll(pageRequest);
    }


}
