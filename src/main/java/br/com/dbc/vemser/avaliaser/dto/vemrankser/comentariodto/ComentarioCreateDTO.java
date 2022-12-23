package br.com.dbc.vemser.avaliaser.dto.vemrankser.comentariodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComentarioCreateDTO {

    @Schema(description = "Feedback da atividade", example = "Pontos de melhoria...")
    private String comentario;
}
