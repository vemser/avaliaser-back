package br.com.dbc.vemser.avaliaser.dto.recuperacao;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRecuperacaoDTO {

    @NotNull
    @Email
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;
    @NotNull
    @NotBlank
    @Schema(example = "Noah Bispo")
    private String nome;
    @NotNull
    @NotBlank
    @Size(max = 16, min = 8)
    private String senha;
}
