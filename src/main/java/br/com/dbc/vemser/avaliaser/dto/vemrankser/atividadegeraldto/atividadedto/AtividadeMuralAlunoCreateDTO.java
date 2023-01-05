package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.Data;

@Data
public class AtividadeMuralAlunoCreateDTO {

     private Integer id;
     private Situacao situacao;
}
