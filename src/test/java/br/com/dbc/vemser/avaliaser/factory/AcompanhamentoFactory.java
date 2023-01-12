package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.EditarAcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;

public class AcompanhamentoFactory {
    public static AcompanhamentoEntity getAcompanhamento() {

        AcompanhamentoEntity acompanhamento = new AcompanhamentoEntity();
        acompanhamento.setIdAcompanhamento(1);
        acompanhamento.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");
        acompanhamento.setDataInicio(LocalDate.of(2022, 12, 01));
        acompanhamento.setDataFim(LocalDate.of(2023, 12, 01));

        return acompanhamento;
    }

    public static AcompanhamentoCreateDTO getAcompanhamentoCreateDTO() {
        AcompanhamentoCreateDTO acompanhamentoCreateDTO = new AcompanhamentoCreateDTO();
        acompanhamentoCreateDTO.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");
        acompanhamentoCreateDTO.setDataInicio(LocalDate.of(2022, 12, 01));
        acompanhamentoCreateDTO.setDataFim(LocalDate.of(2023, 12, 01));

        return acompanhamentoCreateDTO;
    }
    public static AcompanhamentoDTO getAcompanhamentoDTO() {

        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO();
        acompanhamentoDTO.setIdAcompanhamento(1);
        acompanhamentoDTO.setTitulo("Paulo Sergio");
        acompanhamentoDTO.setDataInicio(LocalDate.of(2022, 12, 01));
        acompanhamentoDTO.setDataFim(LocalDate.of(2023, 12, 01));
        return acompanhamentoDTO;
    }
}
