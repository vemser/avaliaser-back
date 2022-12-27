package br.com.dbc.vemser.avaliaser.dto.vemrankser.comentariodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComentarioDTO extends ComentarioCreateDTO {

    //    @NotNull
    @Schema(example = "1")
    // private Integer idComentario;
    private Integer statusComentario;
}
