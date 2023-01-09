package br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedBackCreateDTO {
    @NotNull(message = "Campo Id Aluno não pode ser nulo!")
    @Schema(example = "1")
    private Integer idAluno;
    @NotNull(message = "Campo Id Modulo não pode ser nulo!")
    @Schema(example = "1")
    private Integer idModulo;
    @NotNull(message = "Usuario logado")
    @NotBlank(message = "Usuario logado")
    @Schema(example = "carlos.alberto")
    private String usuarioLogado;
    @NotNull(message = "Descrição não pode ser nulo.")
    @NotBlank(message = "Descrição não pode ficar em branco.")
    @Schema(example = "Texto descritivo")
    private String descricao;
    @NotNull(message = "Tipo não pode ser nulo.")
    @Schema(example = "POSITIVO")
    private Tipo situacao;

}
