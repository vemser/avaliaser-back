package br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback;

import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedBackCreateDTO {
    @Schema(example = "1")
    private Integer idAluno;
    @Schema(example = "1")
    private Integer idModulo;
    @NotNull(message = "Nome instrutor responsável não pode ser nulo.")
    @NotBlank(message = "Nome do instrutor responsável não pode ser nulo.")
    @Schema(example = "Carlos Alberto")
    private String nomeInstrutor;
    @NotNull(message = "Descrição não pode ser nulo.")
    @NotBlank(message = "Descrição não pode ficar em branco.")
    @Schema(example = "Texto descritivo")
    private String descricao;
    @NotNull(message = "Tipo não pode ser nulo.")
    @Schema(example = "POSITIVO")
    private Tipo situacao;

}
