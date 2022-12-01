package br.com.dbc.vemser.avaliaser.dto.avaliacao;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import lombok.Data;

@Data
public class EditarAvaliacaoDTO {
    private Integer idAluno;
    private Integer idAcompanhamento;
    private String descricao;
    private Tipo status;

}
