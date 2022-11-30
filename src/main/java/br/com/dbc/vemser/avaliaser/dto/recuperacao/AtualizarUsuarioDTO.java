package br.com.dbc.vemser.avaliaser.dto.recuperacao;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioDTO {


    @NotNull(message = "Email não pode ser nulo.")
    @Email(message = "Email inválido.", regexp = "^[A-Za-z0-9._%+-]+@dbccompany.com.br$")
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;

    @NotNull(message = "Nome não pode ser nulo.")
    @NotBlank(message = "Nome não pode ficar em branco.")
    @Pattern(regexp = "[\\s[a-zA-Z]*]{0,}", message = "Não permitido números e caracteres especiais.")
    @Schema(example = "Noah Bispo")
    private String nome;
}
