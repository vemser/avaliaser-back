package br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AvaliacaoCreateDTO {
    @NotNull(message = "Acompanhamento não pode ser nulo.")
    @Schema(example = "1")
    private Integer idAcompanhamento;
    @NotNull(message = "Aluno não pode ser nulo.")
    @Schema(example = "1")
    private Integer idAluno;
    @NotNull(message = "Nome do gestor responsavel não pode ser nulo.")
    @NotBlank(message = "Nome do gestor responsavel não pode ser nulo.")
    @Schema(example = "Carlos Alberto")
    private String nomeResponsavel;
    @NotNull(message = "Descrição não pode ser nulo.")
    @NotBlank(message = "Descrição não pode ser nulo.")
    @Schema(example = "Descrição")
    private String descricao;
    @NotNull(message = "Tipo não pode ser nulo.")
    @Schema(example = "POSITIVO")
    private Tipo situacao;
    @NotNull(message = "Data de Criação não pode ser nula.")
    @Schema(example = "2022-12-01")
    private LocalDate dataCriacao;
}
