package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;

public class ClienteFactory {
    public static ClienteEntity getClienteEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setIdCliente(1);
        clienteEntity.setNome("Coca Cola");
        clienteEntity.setTelefone("711112459798");
        clienteEntity.setEmail("cocacolabr@mail.com.br");
        clienteEntity.setAtivo(Ativo.S);
        return clienteEntity;
    }

    public static ClienteCreateDTO getClienteCreateDTO() {
        ClienteCreateDTO clienteCreateDTO = new ClienteCreateDTO();
        clienteCreateDTO.setNome("Coca Cola");
        clienteCreateDTO.setTelefone("711112459798");
        clienteCreateDTO.setEmail("cocacolabr@mail.com.br");
        return clienteCreateDTO;
    }
}
