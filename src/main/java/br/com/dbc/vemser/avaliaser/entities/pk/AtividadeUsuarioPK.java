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
public class AtividadeUsuarioPK implements Serializable {

    @Column(name = "ID_ATIVIDADE")
    private Integer idAtividade;

    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
}
