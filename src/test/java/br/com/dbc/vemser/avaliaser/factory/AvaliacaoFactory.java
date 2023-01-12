package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;

import java.io.IOException;
import java.time.LocalDate;

public class AvaliacaoFactory {
    public static AvaliacaoEntity getAvaliacaoFactory() {
        AvaliacaoEntity avaliacao = new AvaliacaoEntity(1,
                AlunoFactory.getAlunoEntity().getIdAluno(),
                TipoAvaliacao.ATENCAO,
                "avaliando aluno",
                LocalDate.now(),
                Ativo.S,
                new AcompanhamentoEntity(),
                AlunoFactory.getAlunoEntity());
        return avaliacao;
    }

    public static AvaliacaoDTO getAvaliacaoDTOFactory() throws IOException {
        AvaliacaoDTO avaliacao = new AvaliacaoDTO(getAvaliacaoFactory().getIdAvaliacao(),
                getAvaliacaoFactory().getDescricao(),
                getAvaliacaoFactory().getTipoAvaliacao(),
                getAvaliacaoFactory().getDataCriacao(),
                new AcompanhamentoDTO(),
                new AlunoDTO());
        return avaliacao;
    }

    public static AvaliacaoCreateDTO getAvaliacaoCreateDTOFactory() {
        AvaliacaoCreateDTO avaliacao = new AvaliacaoCreateDTO(1,
                AlunoFactory.getAlunoEntity().getIdAluno(),
                "avaliando aluno",
                TipoAvaliacao.ATENCAO,
                LocalDate.now());
        return avaliacao;
    }

    public static EditarAvaliacaoDTO getEditarAvaliacaoDTOFactory() {
        EditarAvaliacaoDTO avaliacao = new EditarAvaliacaoDTO("aluno editado",
                TipoAvaliacao.POSITIVO,
                LocalDate.now().plusDays(1));
        return avaliacao;
    }


}
