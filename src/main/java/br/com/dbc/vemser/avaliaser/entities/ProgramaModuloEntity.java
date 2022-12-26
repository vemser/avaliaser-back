package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.ProgramaModuloPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PROGRAMA_MODULO")
public class ProgramaModuloEntity {

    @EmbeddedId
    private ProgramaModuloPK udPrograModulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idModulo")
    @JoinColumn(name = "ID_MODULO")
    private ModuloEntity modulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPrograma")
    @JoinColumn(name = "ID_PROGRAMA")
    private ProgramaEntity programa;
}
