package br.com.dbc.vemser.avaliaser.dto.feedback;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioRetornoAvaliacaoFeedbackDTO;
import br.com.dbc.vemser.avaliaser.enums.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedBackDTO {
    @Schema(example = "1")
    private Integer idFeedBack;
    @Schema(example = "Texto descritivo")
    private String descricao;
    @Schema(example = "POSITIVO")
    private Tipo tipo;
    private UsuarioRetornoAvaliacaoFeedbackDTO usuarioDTO;
    private AlunoDTO alunoDTO;


}
