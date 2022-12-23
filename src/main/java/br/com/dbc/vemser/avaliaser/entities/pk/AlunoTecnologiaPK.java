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
public class AlunoTecnologiaPK implements Serializable {

    @Column(name = "id_tecnologia")
    private Integer idTecnologia;
    @Column(name = "id_aluno")
    private Integer idAluno;
}
