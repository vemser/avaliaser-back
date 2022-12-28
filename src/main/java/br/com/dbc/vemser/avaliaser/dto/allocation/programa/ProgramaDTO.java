package br.com.dbc.vemser.avaliaser.dto.allocation.programa;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {

    private Integer idPrograma;
    private String nome;
    private String descricao;
    private Situacao situacao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Ativo ativo;
}
