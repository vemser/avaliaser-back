package br.com.dbc.vemser.avaliaser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tecnologia")
public class TecnologiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TECNOLOGIA")
    @SequenceGenerator(name = "SEQ_TECNOLOGIA", sequenceName = "seq_tecnologia", allocationSize = 1)
    @Column(name = "id_tecnologia")
    private Integer idTecnologia;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ALUNO_TECNOLOGIA",
            joinColumns = @JoinColumn(name = "id_tecnologia"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    private Set<AlunoEntity> alunos = new HashSet<>();

}
