package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ModuloAtividadeDTO {
    private Integer idModulo;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Ativo ativo;
}
