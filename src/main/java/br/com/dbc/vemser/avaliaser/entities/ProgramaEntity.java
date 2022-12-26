package br.com.dbc.vemser.avaliaser.entities;


import br.com.dbc.vemser.avaliaser.entities.pk.VagaProgramaPK;
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
@Entity(name = "Programa")
public class ProgramaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROGRAMA_SEQ")
    @SequenceGenerator(name = "PROGRAMA_SEQ", sequenceName = "seq_programa", allocationSize = 1)
    @Column(name = "id_programa")
    private Integer idPrograma;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AlunoEntity> alunos = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "VAGA_PROGRAMA",
            joinColumns = @JoinColumn(name = "id_programa"),
            inverseJoinColumns = @JoinColumn(name = "id_vaga")
    )
    private Set<VagaEntity> vagas = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PROGRAMA_MODULO",
            joinColumns = @JoinColumn(name = "id_programa"),
            inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private Set<ModuloEntity> modulos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AtividadeEntity> atividades = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "programaEntity", fetch = FetchType.LAZY)
    private Set<AvaliacaoEntity> avaliacoes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AcompanhamentoEntity> acompanhamentos = new HashSet<>();

}
