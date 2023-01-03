package br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaCreateDTO {

    @NotNull(message = "Campo não pode ser nulo")
//    @NotBlank(message = "Campo não pode estar em branco")
    @Schema(description = "Nome da tecnologia", example = "JAVA")
    private String nome;
}
