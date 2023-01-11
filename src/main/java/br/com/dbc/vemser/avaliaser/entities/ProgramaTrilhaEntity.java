package br.com.dbc.vemser.avaliaser.entities;


import br.com.dbc.vemser.avaliaser.entities.pk.ProgramaTrilhaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PROGRAMA_TRILHA")
public class ProgramaTrilhaEntity {

    @EmbeddedId
    private ProgramaTrilhaPK idProgramaTrilha;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPrograma")
    @JoinColumn(name = "id_programa")
    private ProgramaEntity programa;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTrilha")
    @JoinColumn(name = "ID_PROGRAMA")
    private TrilhaEntity trilha;
}
