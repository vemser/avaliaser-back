package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import lombok.Data;

@Data
public class AtividadeFiltroDTO {

    private AlunoDTO aluno;
    private AtividadeDTO atividade;
}
