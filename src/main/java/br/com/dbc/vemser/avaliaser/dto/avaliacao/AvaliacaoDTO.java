package br.com.dbc.vemser.avaliaser.dto.avaliacao;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Tipo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoDTO {
    private Integer idAvaliacao;
    private AcompanhamentoDTO acompanhamento;
    private AlunoDTO aluno;
    private UsuarioDTO responsavel;
    private String descricao;
    private Tipo tipo;
    private LocalDate dataCriacao;
}
