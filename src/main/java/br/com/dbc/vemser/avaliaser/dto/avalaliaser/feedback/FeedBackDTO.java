package br.com.dbc.vemser.avaliaser.dto.avalaliaser.feedback;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedBackDTO {

    @Schema(example = "1")
    private Integer idFeedBack;
    @Schema(example = "Texto descritivo")
    private String descricao;
    @Schema(example = "POSITIVO")
    private TipoAvaliacao situacao;
    @Schema(example = "Carlos Alberto")
    private String nomeInstrutor;
    @Schema(example = "2022-12-01")
    private LocalDate data;
    private AlunoDTO alunoDTO;
    private List<ModuloDTO> moduloDTO;

}
