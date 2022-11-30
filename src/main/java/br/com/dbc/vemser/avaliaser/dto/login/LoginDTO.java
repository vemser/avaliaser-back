package br.com.dbc.vemser.avaliaser.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotNull(message = "Email não pode ser nulo.")
    @Email(message = "Email inválido.", regexp = "^[A-Za-z0-9._%+-]+@dbccompany.com.br$")
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    @NotBlank(message = "Senha não pode ficar em branco.")
    @Size(min = 8, max = 16, message = "Senha precisa ter entre 8 e 16 caracteres.")
    private String senha;
}
