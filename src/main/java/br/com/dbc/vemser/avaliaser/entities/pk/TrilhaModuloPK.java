package br.com.dbc.vemser.avaliaser.entities.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class TrilhaModuloPK implements Serializable {

    @Column(name = "id_trilha")
    private Integer idTrilha;
    @Column(name = "id_modulo")
    private Integer idModulo;
}
