package br.com.dbc.vemser.avaliaser.entities;


import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoVagaPrograma;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private SituacaoVagaPrograma situacaoVagaPrograma;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Ativo ativo;

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AlunoEntity> alunos = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Vaga_Programa",
            joinColumns = @JoinColumn(name = "id_programa"),
            inverseJoinColumns = @JoinColumn(name = "id_vaga")
    )
    private Set<VagaEntity> vagas = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Programa_Trilha",
            joinColumns = @JoinColumn(name = "id_programa"),
            inverseJoinColumns = @JoinColumn(name = "id_trilha")
    )
    private Set<TrilhaEntity> trilhas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AtividadeEntity> atividades = new HashSet<>();


    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AcompanhamentoEntity> acompanhamentos = new HashSet<>();

}
