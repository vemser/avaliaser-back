package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrilhaCreateDTO {

    @Schema(description = "Nome", example = "Backend")
    private String nome;

    @Schema(description = "Edição do vem ser", example = "11")
    private Integer edicao;

    @Schema(description = "Ano da edição", example = "15/02/2023")
    private LocalDate anoEdicao;

}
