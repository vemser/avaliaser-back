package br.com.dbc.vemser.avaliaser.dto.allocation.programa;


import io.swagger.v3.oas.annotations.media.Schema;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {
    @Schema(example = "1")
    private Integer idPrograma;
    @Schema(description = "Nome do programa", example = "VemSer 10ed")
    private String nome;
    @Schema(example = "Programa de formação profissional trilha Backend Vem Ser DBC 10º edição.")
    private String descricao;
    @Schema(description = "situacao do programa", example = "ABERTO")
    private Situacao situacao;
    @Schema(description = "Data de abertura programa", example = "2023-02-23")
    private LocalDate dataInicio;
    @Schema(description = "Data de termino do programa", example = "2023-06-23")
    private LocalDate dataFim;

}
