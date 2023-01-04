package br.com.dbc.vemser.avaliaser.services.allocation;

import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.entities.VagaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final ProgramaService programaService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;


    public VagaDTO salvar(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        verificarDatas(vagaCreate);
        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findById(vagaCreate.getIdCliente());
        VagaEntity vagaEntity = converterEntity(vagaCreate);
        vagaEntity.setCliente(cliente);
        vagaEntity.getPrograma().add(programa);
        vagaEntity.setSituacao(vagaCreate.getSituacao());
        vagaEntity.setAtivo(Ativo.S);
        vagaEntity.setDataCriacao(LocalDate.now());
        vagaEntity = vagaRepository.save(vagaEntity);
        verificarClienteInativo(vagaEntity);
        return converterEmDTO(vagaEntity);
    }

    public PageDTO<VagaDTO> listar(Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<VagaEntity> paginaDoRepositorio = vagaRepository.findAll(pageRequest);

        List<VagaDTO> vagas = paginaDoRepositorio.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                page,
                size,
                vagas
        );
        }
        List<VagaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }


    public PageDTO<VagaDTO> listarPorId(Integer idVaga) throws RegraDeNegocioException  {

        List<VagaDTO> list = List.of(converterEmDTO(findById(idVaga)));
        Page<VagaDTO> page = new PageImpl<>(list);

        return new PageDTO<>(page.getTotalElements(),
                page.getTotalPages(),
                0,
                1,
                list
        );

    }

    public PageDTO<VagaDTO> listarPorNome(Integer page, Integer size,String nome) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<VagaEntity> paginaDoRepositorio = vagaRepository.findAllByNomeContainingIgnoreCase(pageRequest, nome);

        List<VagaDTO> vagas = paginaDoRepositorio.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                page,
                size,
                vagas
        );
        }
        List<VagaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public VagaDTO editar(Integer idVaga, VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        verificarDatas(vagaCreate);

        if (vagaCreate.getSituacao().equals(Situacao.FECHADO)) {
            fecharVaga(idVaga);
        }
        VagaEntity vagaEntity = objectMapper.convertValue(vagaCreate, VagaEntity.class);

        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findById(vagaCreate.getIdCliente());
        vagaEntity.setCliente(cliente);
        vagaEntity.getPrograma().add(programa);
        vagaEntity.setIdVaga(idVaga);
        vagaEntity.setDataCriacao(LocalDate.now());

        vagaEntity = vagaRepository.save(vagaEntity);

        return converterEmDTO(vagaRepository.save(vagaEntity));
    }

    public VagaDTO converterEmDTO(VagaEntity vagaEntity) {

        ClienteDTO clienteDTO = clienteService.converterEmDTO(vagaEntity.getCliente());
        List<ProgramaDTO> programaDTO = vagaEntity.getPrograma().stream()
                .map(programa -> programaService.converterEmDTO(programa)).toList();
        VagaDTO vagaDTO = new VagaDTO(vagaEntity.getIdVaga(),
                vagaEntity.getNome(),
                vagaEntity.getQuantidade(),
                vagaEntity.getQuantidadeAlocados(),
                vagaEntity.getSituacao(),
                vagaEntity.getDataAbertura(),
                vagaEntity.getDataFechamento(),
                vagaEntity.getDataCriacao(),
                clienteDTO,
                programaDTO);
        return vagaDTO;
    }

    public VagaEntity converterEntity(VagaCreateDTO vagaCreateDTO) {
        return objectMapper.convertValue(vagaCreateDTO, VagaEntity.class);
    }

    public void deletar(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vaga.setAtivo(Ativo.N);
        vagaRepository.save(vaga);
    }

    public VagaEntity findById(Integer idVaga) throws RegraDeNegocioException {
        return vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }

    public List<VagaDTO> findAllWithSituacaoAberto() {
        return vagaRepository.findBySituacao(Situacao.ABERTO)
                .stream()
                .map(vagaEntity -> objectMapper.convertValue(vagaEntity, VagaDTO.class))
                .collect(Collectors.toList());
    }

    public VagaEntity findByNome(String nome) throws RegraDeNegocioException {
        return vagaRepository.findByNome(nome).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }

    public void alterarQuantidadeDeVagas(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        verificarClienteInativo(vaga);
        if (vaga.getQuantidade() > 0) {
            vaga.setQuantidade(vaga.getQuantidade() - 1);
            vagaRepository.save(vaga);
        } else {
            throw new RegraDeNegocioException("Quantidades de Vagas foram prenchidas!");
        }
    }


    private static void verificarClienteInativo(VagaEntity vaga) throws RegraDeNegocioException {
        if (vaga.getCliente().getAtivo().equals(Ativo.N)) {
            throw new RegraDeNegocioException("Cliente inativo!");
        }
    }

    public void verificarVagaFechada(VagaEntity vaga) throws RegraDeNegocioException {
        if (vaga.getSituacao().equals(Situacao.FECHADO)) {
            throw new RegraDeNegocioException("Vaga Fechada!");
        }
    }

    public void fecharVaga(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        if (vaga.getQuantidade() == 0) {
            vaga.setSituacao(Situacao.FECHADO);
            vagaRepository.save(vaga);
        }
    }

    public void adicionarQuantidadeDeAlocados(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vaga.setQuantidadeAlocados(vaga.getQuantidadeAlocados() + 1);
        vagaRepository.save(vaga);
    }

    public void removerQuantidadeDeAlocados(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        if (vaga.getQuantidadeAlocados() != null){
            vaga.setQuantidadeAlocados(vaga.getQuantidadeAlocados() - 1);
        }else {
            throw new RegraDeNegocioException("Quantidades de alocados null!");
        }
        vagaRepository.save(vaga);
    }



    private static void verificarDatas(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        if (vagaCreate.getDataAbertura().isAfter(vagaCreate.getDataFechamento())) {
            throw new RegraDeNegocioException("Data de abertura não pode ser maior que a data de fechamento no cadastro.");
        }
    }
}
