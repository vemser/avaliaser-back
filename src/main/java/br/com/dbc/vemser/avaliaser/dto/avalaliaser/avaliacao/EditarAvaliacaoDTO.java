package br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EditarAvaliacaoDTO {
    private Integer idAcompanhamento;
    @NotNull(message = "Aluno não pode ser nulo.")
    @Schema(example = "1")
    private Integer idAluno;
    @NotNull(message = "Não pode ser nulo.")
    @NotBlank(message = "Não pode ser nulo.")
    @Schema(example = "Descrição")
    private String descricao;
    @NotNull(message = "Tipo não pode ser nulo.")
    @Schema(example = "POSITIVO")
    private Tipo status;

}
