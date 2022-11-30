package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.AcompanhamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcompanhamentoService {

    private final AcompanhamentoRepository acompanhamentoRepository;
    private final ObjectMapper objectMapper;

    public PageDTO<AcompanhamentoDTO> listarAcompanhamentosPaginados(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AcompanhamentoEntity> paginaDoRepositorio = acompanhamentoRepository.findAll(pageRequest);
        List<AcompanhamentoDTO> acompanhamentoPaginas = paginaDoRepositorio.getContent().stream()
                .map(acompanhamentoEntity -> objectMapper.convertValue(acompanhamentoEntity, AcompanhamentoDTO.class))
                .toList();

        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                acompanhamentoPaginas
        );
    }
    public AcompanhamentoDTO cadastrarAcompanhamento(AcompanhamentoCreateDTO acompanhamentoCreateDTO) throws RegraDeNegocioException {

            AcompanhamentoEntity acompanhamentoEntity = new AcompanhamentoEntity();

            acompanhamentoEntity.setTitulo(acompanhamentoCreateDTO.getTitulo());
            acompanhamentoEntity.setDataInicio(acompanhamentoCreateDTO.getDataInicio());

            AcompanhamentoEntity acompanhamentoSalvo = acompanhamentoRepository.save(acompanhamentoEntity);

        AcompanhamentoDTO acompanhamentoDTO = objectMapper.convertValue(acompanhamentoSalvo, AcompanhamentoDTO.class);
        acompanhamentoDTO.setIdAcompanhamento(acompanhamentoSalvo.getIdAcompanhamento());
        return acompanhamentoDTO;
    }
    public AcompanhamentoDTO editarAcompanhamento(EditarAcompanhamentoDTO editarAcompanhamentoDTO, Integer id) throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamentoEntity = findById(id);
        acompanhamentoEntity.setTitulo(editarAcompanhamentoDTO.getTitulo());
        acompanhamentoRepository.save(acompanhamentoEntity);
        return objectMapper.convertValue(acompanhamentoEntity, AcompanhamentoDTO.class);
    }


    public AcompanhamentoEntity findById(Integer id) throws RegraDeNegocioException {
        return acompanhamentoRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Acompanhamento não encontrado."));
    }

    public AcompanhamentoDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        AcompanhamentoEntity acompanhamentoEntity = findById(id);
        AcompanhamentoDTO acompanhamentoDTO =
                objectMapper.convertValue(acompanhamentoEntity, AcompanhamentoDTO.class);
        return acompanhamentoDTO;
    }



}
