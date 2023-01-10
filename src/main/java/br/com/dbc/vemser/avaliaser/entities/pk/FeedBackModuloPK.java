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
public class FeedBackModuloPK implements Serializable {

    @Column(name = "id_feedback")
    private Integer idFeedBack;
    @Column(name = "id_modulo")
    private Integer idModulo;

}
