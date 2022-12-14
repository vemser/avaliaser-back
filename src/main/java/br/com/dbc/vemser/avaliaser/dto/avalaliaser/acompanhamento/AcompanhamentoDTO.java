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

    private Integer idAcompanhamento;

    private String titulo;

    private ProgramaDTO programa;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private String descricao;


}
