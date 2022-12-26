package br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcompanhamentoDTO {
    @Schema(example = "1")
    private Integer idAcompanhamento;
    @Schema(example = "Acompanhamento 1")
    private String titulo;
    @Schema(example = "Texto descritivo")
    private String descricao;
    @Schema(example = "2022-11-22")
    private LocalDate dataInicio;
    private ProgramaDTO programa;


}
