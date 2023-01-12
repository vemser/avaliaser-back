package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrilhaDTO  {

    private Integer idTrilha;
    private String descricao;
    private String nome;
    private List<ModuloDTO> moduloDTOS;


}
