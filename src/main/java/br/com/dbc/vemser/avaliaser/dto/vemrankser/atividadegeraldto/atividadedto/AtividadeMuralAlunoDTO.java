package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.enums.SituacaoAtividade;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtividadeMuralAlunoDTO {

    private Integer idAtividade;
    private String nomeInstrutor;
    private String titulo;
    private String descricao;
    private Integer pesoAtividade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEntregaAluno;
    private Ativo ativo;
    private ProgramaDTO programa;
    private String link;
    private Integer nota;
    private LocalDateTime dataEntregaLimite;
    private SituacaoAtividade situacao;
}
