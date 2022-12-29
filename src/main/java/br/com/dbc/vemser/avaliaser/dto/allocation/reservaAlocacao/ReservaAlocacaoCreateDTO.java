package br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao;


import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaAlocacaoCreateDTO {
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "id da Vaga", example = "24")
    private Integer idVaga;
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "id do Aluno", example = "8")
    private Integer idAluno;
    @NotNull(message = "Campo não pode ser nulo")
    @NotBlank(message = "Campo não pode estar em branco")
    @Schema(description = "Descrição", example = "O que nós buscamos " +
            "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.")
    private String descricao;
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "Data reserva ", example = "2022-12-22")
    private LocalDate dataReserva;
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "Data alocação ", example = "2022-12-24")
    private LocalDate dataAlocacao;
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "Data cancelamento", example = "2022-12-26")
    private LocalDate dataCancelamento;
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "Data de finalização", example = "2022-12-26")
    private LocalDate dataFinalizado;
    @NotNull(message = "Campo não pode ser nulo")
    @Schema(description = "Status Alocação Aluno", example = "RESERVADO")
    private Situacao situacao;
}
