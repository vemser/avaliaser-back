package br.com.dbc.vemser.avaliaser.dto.avalaliaser.recuperacao;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRecuperacaoDTO {

    @NotNull(message = "Email não pode ser nulo.")
    @Email(message = "Email inválido.", regexp = "^[A-Za-z0-9._%+-]+@dbccompany.com.br$")
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;

    @NotNull(message = "Nome não pode ser nulo.")
    @NotBlank(message = "Nome não pode ficar em branco.")
    @Pattern(regexp = "[\\s[a-zA-Z]*]{0,}", message = "Não permitido números e caracteres especiais.")
    @Schema(example = "Noah Bispo")
    private String nome;

    @NotNull(message = "Senha nova não pode ser nula")
    @NotBlank(message = "Senha nova não pode ficar em branco.")
    @Size(min = 8, max = 16, message = "Senha precisa ter entre 8 e 16 caracteres.")
    private String senha;
}
