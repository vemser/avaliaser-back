package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.ModuloAtividadePK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "MODULO_ATIVIDADE")
public class ModuloAtividadeEntity {

    @EmbeddedId
    private ModuloAtividadePK idModuloAtividade;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idModulo")
    @JoinColumn(name = "ID_MODULO")
    private ModuloEntity modulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAtividade")
    @JoinColumn(name = "ID_ATIVIDADE")
    private AtividadeEntity atividade;

}
