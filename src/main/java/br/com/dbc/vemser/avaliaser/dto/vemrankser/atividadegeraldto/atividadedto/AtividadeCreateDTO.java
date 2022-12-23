package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeCreateDTO {

    @NotEmpty
    @Schema(description = "título da atividade", example = "POO")
    private String titulo;


    @Schema(description = "Instruções para a atividade", example = "Exercício de orientação à objeto")
    private String instrucoes;

    @NotNull
    @Schema(description = "Peso da atividade", example = "2")
    private Integer pesoAtividade;



    @Schema(description = "Data de início da atividade", example = "15/02/2023")
    private LocalDateTime dataCriacao;

    @NotNull
    @Schema(description = "Data final para entrega da atividade", example = "16/02/2023")
    private LocalDateTime dataEntrega;


    private List<Integer> trilhas;

    private Integer idModulo;
}
