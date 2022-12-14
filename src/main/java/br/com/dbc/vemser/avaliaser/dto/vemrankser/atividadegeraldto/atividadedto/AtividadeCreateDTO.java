package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeCreateDTO {

    @NotEmpty
    @Schema(description = "título da atividade", example = "POO")
    private String titulo;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(description = "Peso da atividade", example = "2")
    private Integer pesoAtividade;

    @NotNull
    @FutureOrPresent
    @Schema(description = "Data final para entrega da atividade", example = "16/02/2023")
    private LocalDateTime dataEntrega;

    @Schema(description = "Descrição da atividade", example = "Uma lista de exercicios de Java")
    private String descricao;

    @NotNull(message = "Nome instrutor não pode ser nulo!")
    @NotBlank(message = "Nome instrutor não pode estar em branco!")
    @Schema(description = "Nome do instrutor", example = "Rafa")
    private String nomeInstrutor;

    @Schema(description = "Lista dos modulos", example = "[1, 2, 3]")
    private List<Integer> modulos;

    @Schema(description = "Lista dos alunos", example = "[1, 2, 3]")
    private List<Integer> alunos;

    @NotNull(message = "Programa não pode ficar nulo.")
    @Schema(example = "1")
    private Integer idPrograma;
}
