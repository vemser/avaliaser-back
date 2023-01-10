package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AcompanhamentoRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
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
    private final ProgramaService programaService;

    public PageDTO<AcompanhamentoDTO> listarAcompanhamentosPaginados(Integer idAcompanhamento,
                                                                     String nomePrograma,
                                                                     String tituloAcompanhamento,
                                                                     Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            Page<AcompanhamentoEntity> paginaDoRepositorio = filtrarAcompanhamento(idAcompanhamento, nomePrograma, tituloAcompanhamento, pagina, tamanho);
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
    private Page<AcompanhamentoEntity> filtrarAcompanhamento(Integer idAcompanhamento, String nomePrograma, String tituloAcompanhamento, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idAcompanhamento == null)) {
            return acompanhamentoRepository.findByIdAcompanhamentoAndAtivo(idAcompanhamento, Ativo.S,pageRequest);
        } else if (!(nomePrograma == null)) {
            return acompanhamentoRepository.findAllByPrograma_NomeContainingIgnoreCaseAndAtivo(nomePrograma,Ativo.S,pageRequest);
        } else if(!(tituloAcompanhamento == null)){
            return acompanhamentoRepository.findAllByTituloContainingIgnoreCaseAndAtivo(tituloAcompanhamento, Ativo.S, pageRequest);
        }
        return acompanhamentoRepository.findAllByAtivo(Ativo.S, pageRequest);
    }


    private static void verificarDatas(AcompanhamentoCreateDTO createDTO) throws RegraDeNegocioException {
        if (createDTO.getDataFim().isBefore(createDTO.getDataInicio())) {
            throw new RegraDeNegocioException("A data final do acompanhamento não pode ser inferior a data inicial. Tente novamente!");
        }
    }


}
