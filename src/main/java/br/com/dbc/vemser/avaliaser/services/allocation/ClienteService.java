package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;


    public ClienteDTO salvar(ClienteCreateDTO clienteCreate) throws RegraDeNegocioException {
        try {
            ClienteEntity clienteEntity = converterEntity(clienteCreate);
            clienteEntity.setAtivo(Ativo.S);
            return converterEmDTO(clienteRepository.save(clienteEntity));
        } catch (Exception e){
            throw new RegraDeNegocioException("Email já cadastrado!");
        }
    }

    public PageDTO<ClienteDTO> listar(Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ClienteEntity> paginaRepository = clienteRepository.findAllByAtivo(Ativo.S,pageRequest);

        List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                page,
                size,
                clienteDTOList);
        }
        List<ClienteDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public PageDTO<ClienteDTO> listarInativos(Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ClienteEntity> paginaRepository = clienteRepository.findAllByAtivo(Ativo.N,pageRequest);

            List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    page,
                    size,
                    clienteDTOList);
        }
        List<ClienteDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    @NotNull
    private List<ClienteDTO> getClienteDTOS(Page<ClienteEntity> paginaRepository) {
        List<ClienteDTO> clienteDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .toList();
        return clienteDTOList;
    }

    public PageDTO<ClienteDTO> listarPorEmail(Integer page, Integer size, String email) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ClienteEntity> paginaRepository =
                    clienteRepository.findAllByEmailContainingIgnoreCaseAndAtivo(email, Ativo.S, pageRequest);

            List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    page,
                    size,
                    clienteDTOList);
        }
        List<ClienteDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public PageDTO<ClienteDTO> listarPorNome(Integer page, Integer size, String nome) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ClienteEntity> paginaRepository =
                clienteRepository.findAllByNomeContainingIgnoreCaseAndAtivo(nome, Ativo.S, pageRequest);

        List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                page,
                size,
                clienteDTOList);
        }
        List<ClienteDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteCreate) throws RegraDeNegocioException {
       try {
            this.findById(idCliente);
            ClienteEntity clienteEntity = converterEntity(clienteCreate);
            clienteEntity.setIdCliente(idCliente);
            clienteEntity.setAtivo(findById(idCliente).getAtivo());
            if(!clienteEntity.getEmail().equals(clienteCreate.getEmail())){
                clienteEntity.setEmail(clienteCreate.getEmail());
            }
           clienteEntity = clienteRepository.save(clienteEntity);
            return converterEmDTO(clienteEntity);
        } catch (Exception e){
           throw new RegraDeNegocioException("Email já cadastrado no sistema");
       }
    }

    public void deletar(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);
        clienteEntity.setAtivo(Ativo.N);
        clienteRepository.save(clienteEntity);
    }

    public ClienteDTO reativarCliente (Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findByIdInativos(idCliente);
        clienteEntity.setAtivo(Ativo.S);
        ClienteEntity cliente = clienteRepository.save(clienteEntity);
        return converterEmDTO(cliente);
    }

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteEntity findByIdInativos(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findByIdClienteAndAndAtivo(id, Ativo.N)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente inativo não encontrado"));
    }

    public ClienteEntity findByEmail(String email) throws RegraDeNegocioException {
        return clienteRepository.findByEmailIgnoreCaseAndAtivo(email, Ativo.S);
    }

    public ClienteEntity converterEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }

    public ClienteDTO converterEmDTO(ClienteEntity clienteEntity) {
        return new ClienteDTO(clienteEntity.getIdCliente(),
                clienteEntity.getNome(),
                clienteEntity.getEmail(),
                clienteEntity.getTelefone(),
                clienteEntity.getAtivo());
    }
}
