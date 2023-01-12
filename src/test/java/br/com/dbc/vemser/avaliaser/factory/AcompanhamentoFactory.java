package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento.AcompanhamentoDTO;
import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;

import java.time.LocalDate;

public class AcompanhamentoFactory {

    public static AcompanhamentoEntity getAcompanhamento() {
        ProgramaEntity programaEntity =  ProgramaFactory.getProgramaEntity();
        AcompanhamentoEntity acompanhamento = new AcompanhamentoEntity();
        acompanhamento.setIdAcompanhamento(1);
        acompanhamento.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");
        acompanhamento.setDataInicio(LocalDate.of(2023, 12, 01));
        acompanhamento.setDataFim(LocalDate.of(2024, 12, 01));
        acompanhamento.setPrograma(programaEntity);

        return acompanhamento;
    }

    public static AcompanhamentoCreateDTO getAcompanhamentoCreateDTO() {
        AcompanhamentoCreateDTO acompanhamentoCreateDTO = new AcompanhamentoCreateDTO();
        acompanhamentoCreateDTO.setTitulo("Acompanhamento do aluno: Paulo Sergio - 1");
        acompanhamentoCreateDTO.setDataInicio(LocalDate.of(2023, 12, 01));
        acompanhamentoCreateDTO.setDataFim(LocalDate.of(2024, 12, 01));
        acompanhamentoCreateDTO.setIdPrograma(1);

        return acompanhamentoCreateDTO;
    }
    public static AcompanhamentoDTO getAcompanhamentoDTO() {

        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO();
        ProgramaDTO programaDTO = ProgramaFactory.getProgramaDTO();
        acompanhamentoDTO.setIdAcompanhamento(1);
        acompanhamentoDTO.setTitulo("Paulo Sergio");
        acompanhamentoDTO.setDataInicio(LocalDate.of(2023, 12, 01));
        acompanhamentoDTO.setDataFim(LocalDate.of(2024, 12, 01));
        acompanhamentoDTO.setPrograma(programaDTO);
        return acompanhamentoDTO;
    }
}
