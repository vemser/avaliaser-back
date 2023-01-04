package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Aluno")
public class AlunoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALUNO_SEQUENCIA")
    @SequenceGenerator(name = "ALUNO_SEQUENCIA", sequenceName = "SEQ_ALUNO", allocationSize = 1)
    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "email")
    private String email;

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private Situacao situacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Ativo ativo;

    @Column(name = "id_trilha", updatable = false, insertable = false)
    private Integer idTrilha;

    @Column(name = "id_programa", updatable = false, insertable = false)
    private Integer idPrograma;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunoEntity", cascade = CascadeType.REMOVE)
    private Set<AvaliacaoEntity> avaliacoes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunoEntity", cascade = CascadeType.REMOVE)
    private Set<FeedBackEntity> feedBack;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trilha", referencedColumnName = "id_trilha")
    private TrilhaEntity trilha;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    private ProgramaEntity programa;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ALUNO_TECNOLOGIA",
            joinColumns = @JoinColumn(name = "id_aluno"),
            inverseJoinColumns = @JoinColumn(name = "id_tecnologia")
    )
    private Set<TecnologiaEntity> tecnologia = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    private Set<ReservaAlocacaoEntity> reservaAlocacao;
}
