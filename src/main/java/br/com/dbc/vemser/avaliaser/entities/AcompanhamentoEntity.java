package br.com.dbc.vemser.avaliaser.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "Acompanhamento")
public class AcompanhamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACP_SEQUENCIA")
    @SequenceGenerator(name = "ACP_SEQUENCIA", sequenceName = "SEQ_ACP", allocationSize = 1)
    @Column(name = "id_acompanhamento")
    private Integer idAcompanhamento;

}
