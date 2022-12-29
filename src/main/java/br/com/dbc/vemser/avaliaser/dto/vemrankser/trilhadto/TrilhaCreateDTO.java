package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TrilhaCreateDTO {

    @Schema(description = "Nome", example = "Backend")
    @NotBlank
    private String nome;

    @Schema(description = "Descricao da trilha", example = "Especialidade com a parte mais logica e regra de négocios")
    private String descricao;


}
