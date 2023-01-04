package br.com.dbc.vemser.avaliaser.dto.allocation.programa;

import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do programa", example = "VemSer 10ed")
    private String nome;

    @NotBlank(message = "situacao não pode ser vazio ou nulo.")
    @Schema(description = "situacao do programa", example = "ABERTO")
    private Situacao situacao;

    @Schema(description = "Descrição do programa", example = "Programa de formação profissional trilha Backend Vem Ser DBC 10º edição.")
    private String descricao;


    @Schema(description = "Data de abertura programa", example = "2023-02-23")
    @NotNull
    @FutureOrPresent
    private LocalDate dataInicio;

    @NotNull
    @Schema(description = "Data de termino do programa", example = "2023-06-23")
    @FutureOrPresent
    private LocalDate dataFim;


}
