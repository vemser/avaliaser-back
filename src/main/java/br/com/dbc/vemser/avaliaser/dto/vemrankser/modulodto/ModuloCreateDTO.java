package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class ModuloCreateDTO {

    @NotNull(message = "Campo não pode estar ser nulo!")
    @NotBlank(message = "Campo não pode estar em branco!")
    @Schema(description = "nome do modulo", example = "OOP")
    private String nome;

    @NotEmpty(message = "Campo não pode estar em branco ou nulo!")
    @NotNull(message = "Campo não pode estar em branco ou nulo!")
    @Schema(example = "[1, 2, 3]")
    private List<Integer> listPrograma;
    @NotEmpty(message = "Campo não pode estar em branco ou nulo!")
    @Schema(example = "[1, 2, 3]")
    private List<Integer> trilha;

}
