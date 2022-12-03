package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;

import java.time.LocalDate;

public class AcompanhamentoFactory {
    public static AcompanhamentoEntity getAcompanhamento() {

        AcompanhamentoEntity acompanhamento = new AcompanhamentoEntity();
        acompanhamento.setIdAcompanhamento(1);
        acompanhamento.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");
        acompanhamento.setDataInicio(LocalDate.now());

        return acompanhamento;
    }

    public static AcompanhamentoCreateDTO getAcompanhamentoCreateDTO() {
        AcompanhamentoCreateDTO acompanhamentoCreateDTO = new AcompanhamentoCreateDTO();
        acompanhamentoCreateDTO.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");
        acompanhamentoCreateDTO.setDataInicio(LocalDate.now());

        return acompanhamentoCreateDTO;
    }

    public static EditarAcompanhamentoDTO getEditarAcompanhamento() {
        EditarAcompanhamentoDTO editarAcompanhamentoDTO = new EditarAcompanhamentoDTO();
        editarAcompanhamentoDTO.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");

        return editarAcompanhamentoDTO;
    }

    public static AcompanhamentoDTO getAcompanhamentoDTO() {

        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO();
        acompanhamentoDTO.setIdAcompanhamento(1);
        acompanhamentoDTO.setTitulo("Paulo Sergio");
        acompanhamentoDTO.setDataInicio(LocalDate.of(2022, 12, 01));
        return acompanhamentoDTO;
    }
}
