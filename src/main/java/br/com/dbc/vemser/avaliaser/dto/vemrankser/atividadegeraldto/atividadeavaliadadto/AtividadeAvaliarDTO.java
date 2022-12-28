package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadeavaliadadto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class AtividadeAvaliarDTO {

    @Min(value = 0, message = "deve ser maior ou igual à ZERO")
    @Max(value = 100, message = "deve ser menor ou igual à 100")
    @Schema(description = "Pontução da atividade", example = "90")
    private Integer pontuacao;

}
