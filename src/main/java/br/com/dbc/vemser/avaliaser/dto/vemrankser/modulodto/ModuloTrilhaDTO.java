package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ModuloTrilhaDTO {
    @NotNull(message = "Campo não pode estar ser nulo!")
    @NotBlank(message = "Campo não pode estar em branco!")
    @Schema(description = "Id do modulo", example = "1")
    private Integer idModulo;
    @NotNull(message = "Campo não pode estar ser nulo!")
    @NotBlank(message = "Campo não pode estar em branco!")
    @Schema(description = "Id da trilha", example = "1")
    private Integer idTrilha;
}
