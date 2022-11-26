package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunoEntity")
    private Set<AvaliacaoEntity> avaliacoes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunoEntity")
    private Set<FeedBackEntity> feedBack;


}
