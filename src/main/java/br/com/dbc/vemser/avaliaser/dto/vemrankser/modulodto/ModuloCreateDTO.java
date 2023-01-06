package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;


@Data
public class ModuloCreateDTO {

    @NotNull(message = "Campo não pode estar ser nulo!")
    @NotBlank(message = "Campo não pode estar em branco!")
    @Schema(description = "nome do modulo", example = "OOP")
    private String nome;

    @NotNull(message = "Campo não pode estar em branco ou nulo!")
    @Schema(example = "[1, 2, 3]")
    private List<Integer> listPrograma;

    @Schema(example = "[1, 2, 3]")
    private List<Integer> trilha;


}
