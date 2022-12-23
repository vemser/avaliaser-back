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
public class VagaProgramaPK implements Serializable {

    @Column(name = "id_programa")
    private Integer idPrograma;
    @Column(name = "id_vaga")
    private Integer idVaga;
}
