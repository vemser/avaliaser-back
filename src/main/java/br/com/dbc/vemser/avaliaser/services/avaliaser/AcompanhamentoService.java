package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoFiltroDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoVagaPrograma;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AcompanhamentoRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcompanhamentoService {

    private final AcompanhamentoRepository acompanhamentoRepository;
    private final ObjectMapper objectMapper;

    private final ProgramaService programaService;

    public PageDTO<AcompanhamentoDTO> listarAcompanhamentosPaginados(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<AcompanhamentoEntity> paginaDoRepositorio = acompanhamentoRepository.findAllByAtivo(Ativo.S,pageRequest);
            List<AcompanhamentoDTO> acompanhamentoPaginas = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    acompanhamentoPaginas
            );
        }
        List<AcompanhamentoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public PageDTO<AcompanhamentoFiltroDTO> listarAcompanhamentosPorNomePrograma(String nome, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<AcompanhamentoEntity> paginaDoRepositorio = acompanhamentoRepository.findAllByPrograma_NomeLikeIgnoreCaseAndAtivo(nome,Ativo.S,pageRequest);
            List<AcompanhamentoFiltroDTO> acompanhamento = paginaDoRepositorio.getContent().stream()
                    .map(this::converterFiltroParaDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    acompanhamento
            );
        }
        List<AcompanhamentoFiltroDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }
    public PageDTO<AcompanhamentoDTO> listarAcompanhamentosAtivoPorTitulo(String titulo, Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AcompanhamentoEntity> paginaDoRepositorio = acompanhamentoRepository.findAllByTituloLikeIgnoreCaseAndAtivo(titulo, Ativo.S, pageRequest);
            List<AcompanhamentoDTO> acompanhamentoDTOS = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    page,
                    size,
                    acompanhamentoDTOS);
        }
        List<AcompanhamentoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);

    }

    public AcompanhamentoDTO create(AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = programaService.findById(acompanhamentoCreateDTO.getIdPrograma());
        programaService.verificarProgramaFechado(programaEntity);
        if (acompanhamentoCreateDTO.getDataFim() != null) {
            verificarDatas(acompanhamentoCreateDTO);
        }
        AcompanhamentoEntity acompanhamentoEntity = converterEntity(acompanhamentoCreateDTO, programaEntity);
        AcompanhamentoEntity acompanhamentoSalvo = acompanhamentoRepository.save(acompanhamentoEntity);

        return converterEmDTO(acompanhamentoSalvo);
    }

    public AcompanhamentoDTO update(AcompanhamentoCreateDTO createDTO, Integer id) throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamentoEntity = findById(id);
        ProgramaEntity programaEntity = programaService.findById(createDTO.getIdPrograma());
        programaService.verificarProgramaFechado(programaEntity);
        if (createDTO.getDataFim() != null) {
            verificarDatas(createDTO);
        }
        acompanhamentoEntity.setPrograma(programaEntity);
        acompanhamentoEntity.setTitulo(createDTO.getTitulo());
        acompanhamentoEntity.setDescricao(createDTO.getDescricao());
        acompanhamentoEntity.setDataInicio(createDTO.getDataInicio());
        acompanhamentoEntity.setDataFim(createDTO.getDataFim());

        AcompanhamentoEntity acompanhamentoSave = acompanhamentoRepository.save(acompanhamentoEntity);
        return converterEmDTO(acompanhamentoSave);
    }
    public void desativar(Integer id) throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamentoEntity = findById(id);
        acompanhamentoEntity.setAtivo(Ativo.N);
        acompanhamentoRepository.save(acompanhamentoEntity);
    }


    public AcompanhamentoEntity findById(Integer id) throws RegraDeNegocioException {
        return acompanhamentoRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Acompanhamento não encontrado."));
    }

    public AcompanhamentoDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamentoEntity = findById(id);

        return converterEmDTO(acompanhamentoEntity);
    }

    public AcompanhamentoEntity converterEntity(AcompanhamentoCreateDTO createDTO, ProgramaEntity programaEntity) {
        return new AcompanhamentoEntity(null,
                createDTO.getTitulo(),
                createDTO.getDescricao(),
                Ativo.S,
                createDTO.getDataInicio(),
                createDTO.getDataFim(),
                new HashSet<>(),
                programaEntity);
    }

    public AcompanhamentoDTO converterEmDTO(AcompanhamentoEntity acompanhamento) {
        return new AcompanhamentoDTO(acompanhamento.getIdAcompanhamento(),
                acompanhamento.getTitulo(),
                programaService.converterEmDTO(acompanhamento.getPrograma()),
                acompanhamento.getDataInicio(),
                acompanhamento.getDataFim(),
                acompanhamento.getDescricao());
    }
    public AcompanhamentoFiltroDTO converterFiltroParaDTO(AcompanhamentoEntity acompanhamento){
        return new AcompanhamentoFiltroDTO(acompanhamento.getTitulo(),
                acompanhamento.getPrograma().getNome(),
                acompanhamento.getDataInicio());
    }

    private static void verificarDatas(AcompanhamentoCreateDTO createDTO) throws RegraDeNegocioException {
        if (createDTO.getDataFim().isBefore(createDTO.getDataInicio())) {
            throw new RegraDeNegocioException("A data final do acompanhamento não pode ser inferior a data inicial. Tente novamente!");
        }
    }
    public void verificarAcompanhamentoFechado(AcompanhamentoEntity acompanhamento) throws RegraDeNegocioException {
        if (acompanhamento.getAtivo().equals(Ativo.S)) {
            throw new RegraDeNegocioException("Acompanhamento de id " + acompanhamento.getIdAcompanhamento() + " Inativo");
        }
    }



}
