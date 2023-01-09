package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.AtividadeAlunoPK;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.enums.SituacaoAtividade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String link;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "situacao")
    private SituacaoAtividade situacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAluno")
    @JoinColumn(name = "ID_ALUNO")
    private AlunoEntity aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAtividade")
    @JoinColumn(name = "ID_ATIVIDADE")
    private AtividadeEntity atividade;
}
