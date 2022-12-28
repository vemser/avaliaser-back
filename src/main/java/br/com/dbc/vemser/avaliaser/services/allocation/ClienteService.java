package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
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


    public ClienteDTO salvar(ClienteCreateDTO clienteCreate) {
        ClienteEntity clienteEntity = converterEntity(clienteCreate);
        clienteEntity.setAtivo(Ativo.S);
        return converterEmDTO(clienteRepository.save(clienteEntity));
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
                    clienteRepository.findAllByEmailContainingIgnoreCaseAndAtivo(Ativo.S,pageRequest, email);

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
                clienteRepository.findAllByNomeContainingIgnoreCaseAndAtivo(Ativo.S,pageRequest, nome);

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
        this.findById(idCliente);
        ClienteEntity clienteEntity = converterEntity(clienteCreate);
        clienteEntity.setIdCliente(idCliente);
        clienteEntity = clienteRepository.save(clienteEntity);
        return converterEmDTO(clienteEntity);
    }

    public void deletar(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);
        clienteEntity.setAtivo(Ativo.N);
        clienteRepository.save(clienteEntity);
    }

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteEntity findByEmail(String email) throws RegraDeNegocioException {
        return clienteRepository.findByEmailIgnoreCaseAndAtivo(Ativo.S,email).orElseThrow(() -> new RegraDeNegocioException("Email cliente não encontrado ou não existe."));
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
