package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import lombok.Data;

@Data
public class AtividadeAlunoDTO {
    private Integer idAluno;
    private String nome;
    private String cidade;
    private String estado;
    private String email;
    private String telefone;
    private String descricao;
    private Integer pontuacao;
}
