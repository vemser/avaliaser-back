package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadeavaliadadto;

import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class AtividadeAvaliacaoDTO {

    private Integer idAtividade;

    private Integer idAluno;

    @Min(value = 0, message = "deve ser maior ou igual à ZERO")
    @Max(value = 10, message = "deve ser menor ou igual à 100")
    @Schema(description = "Pontução da atividade", example = "9")
    private Integer notaAvalicao;

    @Schema(description = "Situacao da atividade", example = "P")
    private Situacao situacao;

}
