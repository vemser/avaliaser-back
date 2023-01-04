package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadeentregardto;

import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class AtividadeEntregaCreateDTO {

    private Integer idAtividade;
    private Integer idAluno;
    private String texto;
}
