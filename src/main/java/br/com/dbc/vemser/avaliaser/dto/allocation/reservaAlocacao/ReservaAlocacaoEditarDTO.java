package br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao;

import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReservaAlocacaoEditarDTO {

    @NotNull(message = "Situação não pode ser nulo.")
    @Schema(example = "RESERVADO")
    private SituacaoReserva situacao;

    @Schema(example = "Descrição")
    private String descricao;
}
