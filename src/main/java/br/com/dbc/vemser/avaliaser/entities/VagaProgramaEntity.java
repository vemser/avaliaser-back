package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.VagaProgramaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "VAGA_PROGRAMA")
public class VagaProgramaEntity {

    @EmbeddedId
    private VagaProgramaPK idVagaPrograma;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idVaga")
    @JoinColumn(name = "ID_VAGA")
    private VagaEntity vaga;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPrograma")
    @JoinColumn(name = "ID_PROGRAMA")
    private ProgramaEntity programa;
}