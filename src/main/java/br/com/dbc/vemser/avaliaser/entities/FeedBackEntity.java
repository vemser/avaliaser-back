package br.com.dbc.vemser.avaliaser.entities;

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
    private TipoAvaliacao situacao;

    @Column(name = "nome_instrutor")
    private String nomeInstrutor;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Ativo ativo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    @ToString.Exclude
    private AlunoEntity alunoEntity;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "FEEDBACK_MODULO",
            joinColumns = @JoinColumn(name = "id_feedback"),
            inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private Set<ModuloEntity> moduloEntity = new HashSet<>();
}
