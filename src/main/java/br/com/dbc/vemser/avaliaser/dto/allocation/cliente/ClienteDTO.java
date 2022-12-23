package br.com.dbc.vemser.avaliaser.dto.allocation.cliente;


import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClienteDTO {
    private Integer idCliente;
    private String nome;
    private String email;
    private String telefone;
    private Situacao situacao;

}
