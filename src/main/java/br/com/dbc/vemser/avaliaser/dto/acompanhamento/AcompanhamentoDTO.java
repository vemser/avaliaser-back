package br.com.dbc.vemser.avaliaser.dto.acompanhamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcompanhamentoDTO {

    private Integer idAcompanhamento;

    private String titulo;

    private String descricao;

    private LocalDate dataInicio;


}
