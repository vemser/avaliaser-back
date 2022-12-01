package br.com.dbc.vemser.avaliaser.dto.aluno;

import br.com.dbc.vemser.avaliaser.enums.Stack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunoDTO {
    @Schema(example = "1")
    private Integer idAluno;
    @Schema(example = "Alexandre Bispo")
    private String nome;
    @Schema(example = "alexandre.bispo@dbccompany.com.br")
    private String email;
    @Schema(example = "BACKEND")
    private Stack stack;
    @Schema(example = " ", description = "Imagem de Perfil do Aluno")
    private byte[] foto;
}
