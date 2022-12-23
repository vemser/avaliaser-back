package br.com.dbc.vemser.avaliaser.dto.vemrankser.comentariodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComentarioAvaliacaoDTO {


    @Min(value=0, message="deve ser maior ou igual à ZERO")
    @Max(value=100, message="deve ser menor ou igual à 100")
    @Schema(description = "Pontução da atividade", example = "90")
    private Integer notaAvalicao;

    @Schema(description = "Feedback da atividade", example = "Pontos de melhoria...")
    private String comentario;
}
