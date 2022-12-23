package br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario.UsuarioRetornoAvaliacaoFeedbackDTO;
import br.com.dbc.vemser.avaliaser.enums.Tipo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoDTO {
    private Integer idAvaliacao;
    private String descricao;
    private Tipo tipo;
    private LocalDate dataCriacao;
    private AcompanhamentoDTO acompanhamento;
    private AlunoDTO aluno;
    private UsuarioRetornoAvaliacaoFeedbackDTO responsavel;
}
