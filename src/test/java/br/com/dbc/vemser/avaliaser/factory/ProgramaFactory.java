package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.entities.*;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoVagaPrograma;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgramaFactory {
    public static ProgramaEntity getProgramaEntity(){

        Set<TrilhaEntity> trilhaEntities = new HashSet<>();
        trilhaEntities.add(new TrilhaEntity());
        Set<AlunoEntity> alunoEntities = new HashSet<>();
        alunoEntities.add(new AlunoEntity());
        Set<VagaEntity> vagaEntities = new HashSet<>();
        vagaEntities.add(new VagaEntity());
        Set<AtividadeEntity> atividadeEntities = new HashSet<>();
        atividadeEntities.add(new AtividadeEntity());
        Set<AcompanhamentoEntity> acompanhamentoEntities = new HashSet<>();
        acompanhamentoEntities.add(new AcompanhamentoEntity());
        ProgramaEntity programaEntity = new ProgramaEntity();

        programaEntity.setIdPrograma(1);
        programaEntity.setNome("Programa de Teste");
        programaEntity.setDescricao("Programa para testes unitarios!");
        programaEntity.setAtivo(Ativo.S);
        programaEntity.setDataInicio(LocalDate.of(2023,12,12));
        programaEntity.setDataFim(LocalDate.of(204,12,12));
        programaEntity.setSituacaoVagaPrograma(SituacaoVagaPrograma.ABERTO);
        programaEntity.setAtivo(Ativo.S);
        programaEntity.setTrilhas(trilhaEntities);
        programaEntity.setAlunos(alunoEntities);
        programaEntity.setVagas(vagaEntities);
        programaEntity.setAtividades(atividadeEntities);
        programaEntity.setAcompanhamentos(acompanhamentoEntities);
        return programaEntity;
    }
    public static ProgramaDTO getProgramaDTO(){
        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setIdPrograma(1);
        programaDTO.setNome("Programa de Teste");
        programaDTO.setDescricao("Programa para testes unitarios!");
        programaDTO.setDataInicio(LocalDate.of(2023,12,12));
        programaDTO.setDataFim(LocalDate.of(204,12,12));
        programaDTO.setSituacaoVagaPrograma(SituacaoVagaPrograma.ABERTO);
        return programaDTO;
    }
}
