package br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioLogadoDTO {

    @NotNull(message = "Nome não pode ser nulo.")
    @NotBlank(message = "Nome não pode ficar em branco.")
    @Pattern(regexp = "[\\s[a-zA-Z]*]{0,}", message = "Não permitido números e caracteres especiais.")
    @Schema(example = "Noah Bispo")
    private String nome;
}
