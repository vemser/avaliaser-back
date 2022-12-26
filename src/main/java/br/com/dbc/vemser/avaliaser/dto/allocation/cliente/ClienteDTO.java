package br.com.dbc.vemser.avaliaser.dto.allocation.cliente;


import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClienteDTO {
    @Schema(example = "1")
    private Integer idCliente;
    @Schema(example = "José Antonio")
    private String nome;
    @Schema(example = "joseantonio@email.com")
    private String email;
    @Schema(example = "(11)92345-1234 ou (11)2234-1234")
    private String telefone;
    @Schema(example = "ALOCADO")
    private Situacao situacao;

}
