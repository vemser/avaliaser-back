package br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao;

import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReservaAlocacaoEditarDTO {

    @NotNull(message = "Situação não pode ser nulo.")
    @Schema(example = "RESERVADO")
    private Situacao situacao;

    @NotNull(message = "Descrição não pode ser nulo.")
    @NotBlank(message = "Descrição não pode ficar em branco.")
    @Schema(example = "Descrição")
    private String descricao;
}
