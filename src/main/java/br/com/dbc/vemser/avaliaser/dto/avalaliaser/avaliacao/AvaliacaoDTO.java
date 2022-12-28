package br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoDTO {
    @Schema(example = "1")
    private Integer idAvaliacao;
    @Schema(example = "Adailton Nunes")
    private String nomeResponsavel;
    @Schema(example = "Descrição")
    private String descricao;
    @Schema(example = "POSITIVO")
    private Tipo situacao;
    @Schema(example = "2022-12-01")
    private LocalDate dataCriacao;
    private AcompanhamentoDTO acompanhamento;
    private AlunoDTO aluno;
    private ProgramaDTO programaDTO;
    private TrilhaDTO trilhaDTO;
}
