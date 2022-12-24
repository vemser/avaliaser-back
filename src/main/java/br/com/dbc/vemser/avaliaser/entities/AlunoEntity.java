package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "pontuacao")
    private Integer pontuacao;

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



}
