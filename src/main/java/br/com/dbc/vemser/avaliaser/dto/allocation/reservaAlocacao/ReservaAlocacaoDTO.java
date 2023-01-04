package br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao;


import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaAlocacaoDTO {
    @Schema(example = "1")
    private Integer idReservaAlocacao;
    private VagaDTO vaga;
    private AlunoDTO aluno;
    @Schema(description = "situacao do programa", example = "ABERTO")
    private Situacao situacao;
    private String descricao;

}
