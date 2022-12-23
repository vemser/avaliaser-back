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
@Entity(name = "TRILHA")
public class TrilhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRILHA_SEQUENCIA")
    @SequenceGenerator(name = "TRILHA_SEQUENCIA", sequenceName = "SEQ_TRILHA", allocationSize = 1)
    @Column(name = "id_trilha")
    private Integer idTrilha;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TRILHA_MODULO",
            joinColumns = @JoinColumn(name = "id_trilha"),
            inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private Set<ModuloEntity> modulos = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TRILHA_USUARIO",
            joinColumns = @JoinColumn(name = "id_trilha"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<UsuarioEntity> usuarios = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ATIVIDADE_TRILHA",
            joinColumns = @JoinColumn(name = "id_trilha"),
            inverseJoinColumns = @JoinColumn(name = "id_atividade")
    )
    private Set<AtividadeEntity> atividades = new HashSet<>();
}
