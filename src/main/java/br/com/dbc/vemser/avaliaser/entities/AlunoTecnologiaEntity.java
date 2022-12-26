package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.AlunoTecnologiaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "ALUNO_TECNOLOGIA")
public class AlunoTecnologiaEntity {

    @EmbeddedId
    private AlunoTecnologiaPK idTecnologia;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAluno")
    @JoinColumn(name = "ID_ALUNO")
    private AlunoEntity aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTecnologia")
    @JoinColumn(name = "ID_TECNOLOGIA")
    private TecnologiaEntity tecnologia;
}
