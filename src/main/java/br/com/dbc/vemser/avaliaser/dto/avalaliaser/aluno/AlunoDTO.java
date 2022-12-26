package br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
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
    @Schema(example = "São Paulo")
    private String cidade;
    @Schema(example = "São Paulo")
    private String estado;
    @Schema(example = "alexandre.bispo@dbccompany.com.br")
    private String email;
    @Schema(example = "ALOCADO")
    private Situacao situacao;
    @Schema(example = "Descrição breve do aluno")
    private String descricao;
    @Schema(example = "100")
    private Integer pontuacao;

    private TrilhaDTO trilha;

    private ProgramaDTO programa;

}
