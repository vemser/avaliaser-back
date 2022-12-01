package br.com.dbc.vemser.avaliaser.dto.avaliacao;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class AvaliacaoCreateDTO {
    @NotNull(message = "Acompanhamento não pode ser nulo.")
    private Integer idAcompanhamento;
    @NotNull(message = "Aluno não pode ser nulo.")
    private Integer idAluno;
    @NotNull(message = "Descriçao não pode ser nulo.")
    @NotBlank(message = "Descriçao não pode ser nulo.")
    private String descricao;
    @NotNull(message = "Tipo não pode ser nulo.")
    private Tipo tipo;
    @NotNull(message = "Data de Criação não pode ser nula.")
    @Past(message = "Data não pode ser no passado.")
    @Future(message = "Data não pode ser no futuro.")
    private LocalDate dataCriacao;
}
