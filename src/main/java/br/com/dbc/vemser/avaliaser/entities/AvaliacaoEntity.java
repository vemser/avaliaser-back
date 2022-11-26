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
@Entity(name = "Avaliacao")
public class AvaliacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVALIACAO_SEQUENCIA")
    @SequenceGenerator(name = "AVALIACAO_SEQUENCIA", sequenceName = "SEQ_AVALIACAO", allocationSize = 1)
    @Column(name = "ID_AVALIACAO")
    private Integer idAvaliacao;
}
