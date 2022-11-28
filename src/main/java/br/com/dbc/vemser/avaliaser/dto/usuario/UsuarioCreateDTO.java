package br.com.dbc.vemser.avaliaser.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @NotNull
    @Email
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;
    @NotNull
    @NotBlank
    @Schema(example = "Noah Bispo")
    private String nome;

}
