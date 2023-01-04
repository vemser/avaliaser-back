package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadeentregardto;

import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtividadeEntregaDTO extends AtividadeEntregaCreateDTO{

    private Integer idAtividade;
    private Integer idAluno;
    private String texto;
    private Integer nota;
    private LocalDateTime dataEntrega;
    private Situacao situacao;
}
