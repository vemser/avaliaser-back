package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuloDTO  {

    private Integer idModulo;

    private String nome;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

}
