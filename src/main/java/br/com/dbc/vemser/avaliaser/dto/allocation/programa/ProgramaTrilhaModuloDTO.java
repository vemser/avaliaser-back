package br.com.dbc.vemser.avaliaser.dto.allocation.programa;


import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.SituacaoVagaPrograma;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaTrilhaModuloDTO {
    @Schema(example = "1")
    private Integer idPrograma;
    @Schema(description = "Nome do programa", example = "VemSer 10ed")
    private String nome;
    @Schema(example = "Programa de formação profissional trilha Backend Vem Ser DBC 10º edição.")
    private String descricao;
    @Schema(description = "situacao do programa", example = "ABERTO")
    private SituacaoVagaPrograma situacaoVagaPrograma;
    @Schema(description = "Data de abertura programa", example = "2023-02-23")
    private LocalDate dataInicio;
    @Schema(description = "Data de termino do programa", example = "2023-06-23")
    private LocalDate dataFim;
    private List<TrilhaDTO> trilha;


}
