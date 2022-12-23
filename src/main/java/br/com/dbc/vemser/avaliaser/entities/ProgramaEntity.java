package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "data_termino")
    private LocalDate dataTermino;

    @Column(name = "situacao")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<AlunoEntity> alunos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private Set<VagaEntity> vagas = new HashSet<>();
}
