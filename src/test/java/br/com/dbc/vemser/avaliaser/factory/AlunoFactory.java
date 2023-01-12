package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.modulodto.ModuloDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlunoFactory {
    public static AlunoEntity getAlunoEntity() {
        AlunoEntity aluno = new AlunoEntity();
        aluno.setIdAluno(1);
        aluno.setNome("Paulo Sergio");
        aluno.setEmail("paulo.sergio@dbccompany.com.br");
        aluno.setCidade("São Paulo");
        aluno.setEstado("São Paulo");
        aluno.setTelefone("(11)97163-5381");
        aluno.setDescricao("Descrição");
        aluno.setAtivo(Ativo.S);
        aluno.setSituacao(SituacaoReserva.DISPONIVEL);
        aluno.setTecnologia(Set.of(new TecnologiaEntity()));
        aluno.setTrilha(new TrilhaEntity());
        aluno.setPrograma(new ProgramaEntity());
        aluno.setPontuacao(0);
        return aluno;
    }

    public static AlunoCreateDTO getAlunoCreateDTO() {
        List<Integer> tecnologias = new ArrayList<>();
        tecnologias.add(1);
        AlunoCreateDTO aluno = new AlunoCreateDTO();
        aluno.setNome("Paulo Sergio");
        aluno.setEmail("paulo.sergio@dbccompany.com.br");
        aluno.setCidade("São Paulo");
        aluno.setEstado("São Paulo");
        aluno.setTelefone("(11)97163-5381");
        aluno.setDescricao("Descrição");
        aluno.setSituacao(SituacaoReserva.DISPONIVEL);
        aluno.setTecnologias(tecnologias);
        aluno.setIdTrilha(1);
        aluno.setIdPrograma(1);
        return aluno;
    }

    public static AlunoDTO getAlunoDTO() {
        AlunoDTO aluno = new AlunoDTO();
        aluno.setIdAluno(1);
        aluno.setNome("Paulo Sergio");
        aluno.setEmail("paulo.sergio@dbccompany.com");
        aluno.setCidade("São Paulo");
        aluno.setEstado("São Paulo");
        aluno.setTelefone("(11)97163-5381");
        aluno.setDescricao("Descrição");
        aluno.setSituacao(SituacaoReserva.DISPONIVEL);
        aluno.setTecnologias(List.of(new TecnologiaDTO()));
        List<ModuloDTO> modulos = new ArrayList<>();
        modulos.add(new ModuloDTO());
        aluno.setTrilha(new TrilhaDTO(1, "minha trilha", "Backend", modulos));
        aluno.setPrograma(new ProgramaDTO());
        aluno.setPontuacao(0);
        return aluno;
    }

}
