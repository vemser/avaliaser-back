package br.com.dbc.vemser.avaliaser.dto.allocation.cliente;


import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClienteCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do Cliente", example = "Sicred")
    private String nome;

    @NotBlank(message = "Email não pode ser vazio ou nulo.")
    @Schema(description = "email", example = "sicred@dbccompany.com.br")
    @Email
    private String email;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Telefone do cliente", example = "911234-9876")
    private String telefone;

    @NotNull
    @Schema(description = "Situação do cliente", example = "ATIVO")
    private Situacao situacao;
}
