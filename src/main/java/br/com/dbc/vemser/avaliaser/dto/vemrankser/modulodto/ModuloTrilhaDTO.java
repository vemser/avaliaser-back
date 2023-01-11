package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ModuloTrilhaDTO {
    @Schema(description = "Id do modulo", example = "1")
    private Integer idModulo;
    @Schema(description = "nome do modulo", example = "OOP")
    private String nome;
}
