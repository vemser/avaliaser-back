package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.avaliacao.EditarAvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioRetornoAvaliacaoFeedbackDTO;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.enums.Tipo;

import java.io.IOException;
import java.time.LocalDate;

public class AvaliacaoFactory {
    public static AvaliacaoEntity getAvaliacaoFactory() {
        AvaliacaoEntity avaliacao = new AvaliacaoEntity();
        avaliacao.setIdAvaliacao(1);
        avaliacao.setAcompanhamentoEntity(AcompanhamentoFactory.getAcompanhamento());
        avaliacao.setAlunoEntity(AlunoFactory.getAlunoEntity());
        avaliacao.setUsuarioEntity(UsuarioFactory.getUsuarioEntity());
        avaliacao.setDescricao("Descrição Bala");
        avaliacao.setTipo(Tipo.POSITIVO);
        avaliacao.setDataCriacao(LocalDate.of(2022, 12, 01));
        return avaliacao;
    }

    public static AvaliacaoDTO getAvaliacaoDTOFactory() throws IOException {
        UsuarioRetornoAvaliacaoFeedbackDTO usuario = new UsuarioRetornoAvaliacaoFeedbackDTO();
        usuario.setNome("Vanderlei");
        usuario.setEmail("vanderlei@dbccompany.com.br");
        AvaliacaoDTO avaliacao = new AvaliacaoDTO();
        avaliacao.setIdAvaliacao(1);
        avaliacao.setAcompanhamento(AcompanhamentoFactory.getAcompanhamentoDTO());
        avaliacao.setAluno(AlunoFactory.getAlunoDTO());
        avaliacao.setResponsavel(usuario);
        avaliacao.setDescricao("Descrição Bala");
        avaliacao.setTipo(Tipo.POSITIVO);
        avaliacao.setDataCriacao(LocalDate.of(2022, 12, 01));
        return avaliacao;
    }

    public static AvaliacaoCreateDTO getAvaliacaoCreateDTOFactory() {
        AvaliacaoCreateDTO avaliacao = new AvaliacaoCreateDTO();
        avaliacao.setIdAcompanhamento(1);
        avaliacao.setIdAluno(1);
        avaliacao.setDescricao("Descrição Bala");
        avaliacao.setTipo(Tipo.POSITIVO);
        avaliacao.setDataCriacao(LocalDate.of(2022, 12, 01));
        return avaliacao;
    }

    public static EditarAvaliacaoDTO getEditarAvaliacaoDTOFactory() {
        EditarAvaliacaoDTO avaliacao = new EditarAvaliacaoDTO();
        avaliacao.setIdAcompanhamento(1);
        avaliacao.setIdAluno(1);
        avaliacao.setDescricao("Descrição Chiclete");
        return avaliacao;
    }


}
