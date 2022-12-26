package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.AtividadeAlunoPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "ATIVIDADE_ALUNO")
public class AtividadeAlunoEntity {

    @EmbeddedId
    private AtividadeAlunoPK atividadeAluno;
    @Column(name = "nota")
    private Integer nota;
    @Column(name = "link")
    private String Link;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAluno")
    @JoinColumn(name = "ID_ALUNO")
    private AlunoEntity aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAtividade")
    @JoinColumn(name = "ID_ATIVIDADE")
    private AtividadeEntity atividade;
}
