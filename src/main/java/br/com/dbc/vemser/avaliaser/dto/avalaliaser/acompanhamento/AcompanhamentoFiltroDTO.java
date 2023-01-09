package br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcompanhamentoFiltroDTO {

    private String titulo;

    private String nome;

    private LocalDate dataInicio;

}
