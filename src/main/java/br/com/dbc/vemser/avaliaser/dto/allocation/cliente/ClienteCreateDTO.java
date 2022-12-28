package br.com.dbc.vemser.avaliaser.dto.allocation.cliente;


import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "^([1-9]{2})(?:[2-8]|9[1-9])[0-9]{3}-[0-9]{4}", message = "Deve seguir o exemplo a seguir: (11)92345-1234 ")
    @Schema(example = "(11)92345-1234 ou (11)2234-1234")
    private String telefone;

}
