package br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaDTO extends TecnologiaCreateDTO {

    @Schema(description = "Nome da tecnologia", example = "JAVA")
    private Integer idTecnologia;
}
