package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;


    public ClienteDTO salvar(ClienteCreateDTO clienteCreate) {
        ClienteEntity clienteEntity = converterEntity(clienteCreate);
        clienteEntity.setSituacao(clienteCreate.getSituacao());
        return converterEmDTO(clienteRepository.save(clienteEntity));
    }

    public PageDTO<ClienteDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ClienteEntity> paginaRepository = clienteRepository.findAll(pageRequest);

        List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                clienteDTOList);
    }

    @NotNull
    private List<ClienteDTO> getClienteDTOS(Page<ClienteEntity> paginaRepository) {
        List<ClienteDTO> clienteDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .toList();
        return clienteDTOList;
    }

    public PageDTO<ClienteDTO> listarPorEmail(Integer pagina, Integer tamanho, String email) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ClienteEntity> paginaRepository = clienteRepository.findAllByEmailContainingIgnoreCase(pageRequest, email);

        List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                clienteDTOList);
    }

    public PageDTO<ClienteDTO> listarPorNome(Integer pagina, Integer tamanho, String nome) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ClienteEntity> paginaRepository = clienteRepository.findAllByNomeContainingIgnoreCase(pageRequest, nome);

        List<ClienteDTO> clienteDTOList = getClienteDTOS(paginaRepository);

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                clienteDTOList);
    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteCreate) throws RegraDeNegocioException {
        this.findById(idCliente);
        ClienteEntity clienteEntity = converterEntity(clienteCreate);
        clienteEntity.setIdCliente(idCliente);
        clienteEntity.setSituacao(clienteCreate.getSituacao());
        clienteEntity = clienteRepository.save(clienteEntity);
        return converterEmDTO(clienteEntity);
    }

    public void deletar(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);
        clienteRepository.delete(clienteEntity);
    }

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteEntity findByEmail(String email) throws RegraDeNegocioException {
        return clienteRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new RegraDeNegocioException("Email cliente não encontrado ou não existe."));
    }

    public ClienteEntity converterEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }

    public ClienteDTO converterEmDTO(ClienteEntity clienteEntity) {
        return new ClienteDTO(clienteEntity.getIdCliente(),
                clienteEntity.getNome(),
                clienteEntity.getEmail(),
                clienteEntity.getTelefone(),
                clienteEntity.getSituacao());
    }
}
