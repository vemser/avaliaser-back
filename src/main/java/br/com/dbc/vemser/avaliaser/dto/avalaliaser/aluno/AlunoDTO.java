package br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunoDTO {

    private Integer idAluno;

    private String nome;

    private String cidade;

    private String estado;

    private String email;

    private String telefone;

    private SituacaoReserva situacao;

    private String descricao;

    private Integer pontuacao;

    private List<TecnologiaDTO> tecnologias;

    private TrilhaDTO trilha;

    private ProgramaDTO programa;

}
