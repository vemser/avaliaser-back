package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.TrilhaModuloPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "TRILHA_MODULO")
public class TrilhaModuloEntity {

    @EmbeddedId
    private TrilhaModuloPK idTrilhaModulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTrilha")
    @JoinColumn(name = "ID_TRILHA")
    private TrilhaEntity trilha;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idModulo")
    @JoinColumn(name = "ID_MODULO")
    private ModuloEntity modulo;
}
