package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import lombok.Data;

@Data
public class TrilhaDTO extends TrilhaCreateDTO {

    private Integer idTrilha;
    private Ativo ativo;


}
