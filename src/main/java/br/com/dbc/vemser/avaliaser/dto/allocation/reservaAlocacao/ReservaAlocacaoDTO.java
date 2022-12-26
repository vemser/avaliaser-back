package br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao;


import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaAlocacaoDTO {

    private Integer idReservaAlocacao;
    private VagaDTO vaga;
    private AlunoDTO aluno;
    private Situacao situacao;
    private String motivo;
    private LocalDate dataReserva;
    private LocalDate dataAlocacao;
    private LocalDate dataCancelamento;
    private LocalDate dataFinalizado;

}
