package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Modulo")
public class ModuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULO_SEQUENCIA")
    @SequenceGenerator(name = "MODULO_SEQUENCIA", sequenceName = "SEQ_MODULO", allocationSize = 1)
    @Column(name = "id_modulo")
    private Integer idModulo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Ativo ativo;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TRILHA_MODULO",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_trilha")
    )
    private Set<TrilhaEntity> trilha = new HashSet<>();

    //    @JsonIgnore
//    @OneToMany(mappedBy = "modulos", fetch = FetchType.LAZY)
//    private Set<AtividadeEntity> atividades = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "FEEDBACK_MODULO",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_feedback")
    )
    private Set<FeedBackEntity> feedBack = new HashSet<>();
}


