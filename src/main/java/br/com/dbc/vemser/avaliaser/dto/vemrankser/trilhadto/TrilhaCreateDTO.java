package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TrilhaCreateDTO {
    @NotNull
    @Schema(description = "ID Programa", example = "1")
    private Integer idPrograma;
    @NotNull(message = "Nome não pode ser nulo!")
    @Schema(description = "Nome", example = "Backend")
    private String nome;
    @Schema(description = "Descricao da trilha", example = "Especialidade com a parte mais logica e regra de négocios")
    private String descricao;


}
