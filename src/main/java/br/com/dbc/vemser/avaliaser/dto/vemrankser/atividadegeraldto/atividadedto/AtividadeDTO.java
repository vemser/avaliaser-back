package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class AtividadeDTO {
    private Integer idAtividade;
    private String nomeInstrutor;
    private String titulo;
    private String descricao;
    private Integer pesoAtividade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEntrega;
//    private Situacao situacao;
    private Ativo ativo;
    private ProgramaDTO programa;
    private List<AtividadeAlunoDTO> alunos;
    private List<ModuloAtividadeDTO> modulos;
}
