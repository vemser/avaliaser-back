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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final ProgramaService programaService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;


    public VagaDTO salvar(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        verificarDataCreate(vagaCreate);
        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findById(vagaCreate.getIdCliente());
        VagaEntity vagaEntity = converterEntity(vagaCreate);
        vagaEntity.setCliente(cliente);
        vagaEntity.getPrograma().add(programa);
        vagaEntity.setSituacao(vagaCreate.getSituacao());
        vagaEntity.setAtivo(Ativo.S);
        vagaEntity.setDataCriacao(LocalDate.now());
        vagaEntity.setDataAbertura(vagaCreate.getDataAbertura());
        vagaEntity.setDataFechamento(vagaCreate.getDataFechamento());
        VagaEntity vagaSalva = vagaRepository.save(vagaEntity);
        verificarClienteInativo(vagaSalva);
        return converterEmDTO(vagaSalva);
    }

    public PageDTO<VagaDTO> listByName(Integer idVaga, String nome, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            Page<VagaEntity> paginaDoRepositorio = filtroVaga(idVaga, nome, pagina, tamanho);
            List<VagaDTO> moduloDTOS = paginaDoRepositorio.getContent().stream()
                    .map(this::converterEmDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    moduloDTOS);
        }
        List<VagaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public VagaDTO editar(Integer idVaga, VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        verificarDataCreate(vagaCreate);
        VagaEntity vagaEntity = findById(idVaga);
        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findById(vagaCreate.getIdCliente());
        vagaEntity.setCliente(cliente);

        vagaEntity.getPrograma().clear();
        vagaEntity.getPrograma().add(programa);

        vagaEntity.setQuantidade(vagaCreate.getQuantidade());
        vagaEntity.setNome(vagaCreate.getNome());
        vagaEntity.setSituacao(vagaCreate.getSituacao());
        vagaEntity.setDataAbertura(vagaCreate.getDataAbertura());
        vagaEntity.setDataFechamento(vagaCreate.getDataFechamento());
        vagaEntity.setAtivo(Ativo.S);
        vagaEntity = vagaRepository.save(vagaEntity);

        return converterEmDTO(vagaEntity);
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

    public void desativar(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vaga.setAtivo(Ativo.N);
        vagaRepository.save(vaga);
    }

    public VagaEntity findById(Integer idVaga) throws RegraDeNegocioException {
        return vagaRepository.findByIdVagaAndAtivo(idVaga, Ativo.S).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }

    public void alterarQuantidadeDeVagas(Integer idVaga, boolean adicionar) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        verificarClienteInativo(vaga);
        if(adicionar == true){
            if (vaga.getQuantidade() > 0 && !(vaga.getQuantidade() <= 0)) {
                vaga.setQuantidade(vaga.getQuantidade() - 1);
                vagaRepository.save(vaga);
            } else {
                throw new RegraDeNegocioException("Quantidades de Vagas foram prenchidas!");
            }
        }else{
            if (vaga.getQuantidade() >= 0) {
                vaga.setQuantidade(vaga.getQuantidade() + 1);
                vagaRepository.save(vaga);
            } else {
                throw new RegraDeNegocioException("Não foi possivel alterar a quantidade de vagas.");
            }
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

    public VagaDTO fecharVaga(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        if (vaga.getQuantidade() == 0) {
            vaga.setSituacao(Situacao.FECHADO);
            vagaRepository.save(vaga);
        }
        return converterEmDTO(vaga);
    }

    public void adicionarQuantidadeDeAlocados(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vaga.setQuantidadeAlocados(vaga.getQuantidadeAlocados() + 1);
        alterarQuantidadeDeVagas(idVaga, true);
        vagaRepository.save(vaga);
    }

    public void removerQuantidadeDeAlocados(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        if (vaga.getQuantidadeAlocados() != null && !(vaga.getQuantidadeAlocados() <= 0)){
            vaga.setQuantidadeAlocados(vaga.getQuantidadeAlocados() - 1);
            alterarQuantidadeDeVagas(idVaga, false);
        }else {
            throw new RegraDeNegocioException("Quantidade de alocados não pode ficar menor que zero!");
        }
        vagaRepository.save(vaga);
    }
    public void verificarDataCreate(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        if (vagaCreate.getDataAbertura().isAfter(vagaCreate.getDataFechamento())) {
            throw new RegraDeNegocioException("Data de abertura não pode ser maior que a data de fechamento no cadastro.");
        }
    }


    private Page<VagaEntity> filtroVaga(Integer idVaga, String nome, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idVaga == null)) {
            return vagaRepository.findByIdVagaAndAtivo(pageRequest, idVaga, Ativo.S);
        } else if (!(nome == null)) {
            return vagaRepository.findAllByNomeContainingIgnoreCaseAndAtivo(pageRequest, nome, Ativo.S);
        }
        return vagaRepository.findAllByAtivo(pageRequest, Ativo.S);
    }
}
