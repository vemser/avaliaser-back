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
public class ModuloAtividadePK implements Serializable {

    @Column(name = "id_atividade")
    private Integer idAtividade;
    @Column(name = "id_modulo")
    private Integer idModulo;
}
