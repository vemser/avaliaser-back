package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuloDTO  {
    @Schema(description = "Id do modulo", example = "1")
    private Integer idModulo;
    @Schema(description = "nome do modulo", example = "OOP")
    private String nome;
    @Schema(description = "Data inicial do modulo ", example = "26/11/2022")
    private LocalDate dataInicio;
    @Schema(description = "Data para o fim do modulo ", example = "30/11/2022")
    private LocalDate dataFim;
    @Schema(description = "Status do modulo", example = "ATIVO")
    private Ativo ativo;
    private TrilhaDTO trilhaDTO;
    private List<ProgramaDTO> listProgramaDTO;

}
