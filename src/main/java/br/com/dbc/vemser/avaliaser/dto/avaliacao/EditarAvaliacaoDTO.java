package br.com.dbc.vemser.avaliaser.dto.avaliacao;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EditarAvaliacaoDTO {
    @Schema(example = "1")
    private Integer idAluno;
    @Schema(example = "1")
    private Integer idAcompanhamento;
    @Schema(example = "Descrição")
    private String descricao;
    @Schema(example = "POSITIVO")
    private Tipo status;

}
