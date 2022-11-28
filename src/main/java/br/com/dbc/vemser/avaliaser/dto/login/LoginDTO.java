package br.com.dbc.vemser.avaliaser.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotNull
    @Email
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;
    @NotNull
    @NotBlank
    @Size(max = 16, min = 8)
    private String senha;
}
