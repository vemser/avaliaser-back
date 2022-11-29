package br.com.dbc.vemser.avaliaser.dto.acompanhamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcompanhamentoDTO {
    @NotNull
    private Integer idAcompanhamento;
    @NotNull
    @NotBlank
    private String titulo;
    @NotNull
    @NotBlank
    private LocalDate dataInicio;
}
