package br.com.dbc.vemser.avaliaser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "MODULO")
public class ModuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULO_SEQUENCIA")
    @SequenceGenerator(name = "MODULO_SEQUENCIA", sequenceName = "SEQ_MODULO", allocationSize = 1)
    @Column(name = "id_modulo")
    private Integer idModulo;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TRILHA_MODULO",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_trilha")
    )
    private Set<TrilhaEntity> trilhas = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MODULO_ATIVIDADE",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_atividade")
    )
    private Set<ModuloEntity> modulos = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PROGRAMA_MODULO",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_programa")
    )
    private Set<ProgramaEntity> programas = new HashSet<>();

    //    @JsonIgnore
//    @OneToMany(mappedBy = "modulos", fetch = FetchType.LAZY)
//    private Set<AtividadeEntity> atividades = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_feedback",referencedColumnName = "id_feedback")
    private FeedBackEntity feedBack;
}


