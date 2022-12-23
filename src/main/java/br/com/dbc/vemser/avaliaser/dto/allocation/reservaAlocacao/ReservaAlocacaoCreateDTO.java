package br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao;


import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaAlocacaoCreateDTO {

    @Schema(description = "id da Vaga", example = "24")
    private Integer idVaga;

    @Schema(description = "id do Aluno", example = "8")
    private Integer idAluno;

    @Schema(description = "Descrição", example = "O que nós buscamos " +
            "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.")
    private String descricao;

    @Schema(description = "Data reserva ", example = "2022-12-22")
    private LocalDate dataReserva;

    @Schema(description = "Data alocação ", example = "2022-12-24")
    private LocalDate dataAlocacao;

    @Schema(description = "Data cancelamento", example = "2022-12-26")
    private LocalDate dataCancelamento;

    @Schema(description = "Data cancelamento", example = "2022-12-26")
    private LocalDate dataFinalizado;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status Alocação Aluno", example = "RESERVADO")
    private Situacao situacao;
}
