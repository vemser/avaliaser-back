package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.entities.pk.AtividadeAlunoPK;
import br.com.dbc.vemser.avaliaser.entities.pk.FeedBackModuloPK;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "FEEDBACK_MODULO")
public class FeedBackModuloEntity {

    @EmbeddedId
    private FeedBackModuloPK feedBackModuloPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFeedBack")
    @JoinColumn(name = "ID_FEEDBACK")
    private FeedBackEntity feedBack;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idModulo")
    @JoinColumn(name = "ID_MODULO")
    private ModuloEntity modulo;

}
