package br.com.dbc.vemser.avaliaser.entities;

import br.com.vemrankser.ranqueamento.entity.pk.AtividadeTrilhaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "ATIVIDADE_USUARIO")
public class AtividadeUsuarioEntity {

    @EmbeddedId
    private AtividadeTrilhaPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAtividade")
    @JoinColumn(name = "ID_ATIVIDADE")
    private AtividadeEntity atividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTrilha")
    @JoinColumn(name = "ID_USUARIO")
    private UsuarioEntity usuarioEntity;
}
