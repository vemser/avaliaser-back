package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
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

    private Integer idModulo;

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Ativo ativo;

    private TrilhaDTO trilhaDTO;

    private List<ProgramaDTO> listProgramaDTO;

}
