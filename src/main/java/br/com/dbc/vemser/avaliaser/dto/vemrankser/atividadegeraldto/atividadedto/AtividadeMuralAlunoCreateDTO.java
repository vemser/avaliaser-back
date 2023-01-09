package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import br.com.dbc.vemser.avaliaser.enums.SituacaoAtividade;
import lombok.Data;

@Data
public class AtividadeMuralAlunoCreateDTO {

     private Integer id;
     private SituacaoAtividade situacao;
}
