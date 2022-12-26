package br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
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
