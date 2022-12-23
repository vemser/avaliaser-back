package br.com.dbc.vemser.avaliaser.dto.vemrankser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeNotaDTO {

    private String nome;
    private Integer idAtividade;
    private Integer nota;
}
