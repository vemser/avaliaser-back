package br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import lombok.Data;

import java.util.List;

@Data
public class TrilhaDTO  {

    private Integer idTrilha;
    private String descricao;
    private String nome;
    private List<ProgramaDTO> programaDTO;


}
