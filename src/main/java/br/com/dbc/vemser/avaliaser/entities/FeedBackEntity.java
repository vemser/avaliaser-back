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
@Entity(name = "Feedback")
public class FeedBackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEED_SEQUENCIA")
    @SequenceGenerator(name = "FEED_SEQUENCIA", sequenceName = "SEQ_FEEDBACK", allocationSize = 1)
    @Column(name = "id_feedback")
    private Integer idFeedBack;
}
