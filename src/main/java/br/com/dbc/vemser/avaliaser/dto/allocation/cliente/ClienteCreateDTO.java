package br.com.dbc.vemser.avaliaser.dto.allocation.cliente;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @Schema(example = "(11)92345-1234 ou (11)2234-1234")
    @Pattern(regexp = "^\\([1-9]{2}\\)(?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}", message = "Permitido apenas Telefone Fixo ou Celular.")
    private String telefone;

}
