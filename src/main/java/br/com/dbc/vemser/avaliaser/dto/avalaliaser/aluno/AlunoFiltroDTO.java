package br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AlunoFiltroDTO {

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


}
