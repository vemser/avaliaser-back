package br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao;

import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EditarAvaliacaoDTO {

    @NotNull(message = "Não pode ser nulo.")
    @NotBlank(message = "Não pode ser nulo.")
    @Schema(example = "Descrição")
    private String descricao;

    @NotNull(message = "Situação não pode ser nulo.")
    @Schema(example = "POSITIVO")
    private TipoAvaliacao tipoAvaliacao;

    @Schema(example = "2022-12-01")
    private LocalDate dataCriacao;

}
