package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Feedback")
public class FeedBackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEED_SEQUENCIA")
    @SequenceGenerator(name = "FEED_SEQUENCIA", sequenceName = "SEQ_FEEDBACK", allocationSize = 1)
    @Column(name = "id_feedback")
    private Integer idFeedBack;
    @Column(name = "id_aluno", insertable = false, updatable = false)
    private Integer idAluno;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "situacao")
    private Tipo situacao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    @ToString.Exclude
    private AlunoEntity alunoEntity;
}
