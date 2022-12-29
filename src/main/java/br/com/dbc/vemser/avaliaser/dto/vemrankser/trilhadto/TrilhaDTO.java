package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import lombok.*;

@Data
public class TrilhaDTO  {

    private Integer idTrilha;
    private Ativo ativo;
    private String nome;


}
