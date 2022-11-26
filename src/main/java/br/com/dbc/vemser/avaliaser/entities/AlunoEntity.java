package br.com.dbc.vemser.avaliaser.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
}
