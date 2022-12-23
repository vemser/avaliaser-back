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
@Entity(name = "Alunos")
public class AlunoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALUNOS_SEQUENCIA")
    @SequenceGenerator(name = "ALUNOS_SEQUENCIA", sequenceName = "SEQ_ALUNOS", allocationSize = 1)
    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column(name = "nome")
    private String nome;

    @Column(name = "stack")
    private Stack stack;

    @Column(name = "email")
    private String email;

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "ativo")
    private Ativo ativo;

    @Column(name = "status_aluno")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunoEntity", cascade = CascadeType.REMOVE)
    private Set<AvaliacaoEntity> avaliacoes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunoEntity", cascade = CascadeType.REMOVE)
    private Set<FeedBackEntity> feedBack;



}
