package br.com.dbc.vemser.avaliaser.dto.vemrankser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ModuloCreateDTO {

    @NotNull
    private String nome;

    @Schema(description = "Data inicial do modulo ", example = "26/11/2022")
    private LocalDateTime dataInicio;


    @Schema(description = "Data para o fim do modulo ", example = "30/11/2022")
    private LocalDateTime dataFim;


}
