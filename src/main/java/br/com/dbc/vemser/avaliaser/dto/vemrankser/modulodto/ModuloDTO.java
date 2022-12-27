package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import lombok.*;

import java.time.LocalDate;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuloDTO  {

    private Integer idModulo;

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataFim;

}
