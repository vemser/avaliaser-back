package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "COMENTARIO")
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMENTARIO_SEQUENCIA")
    @SequenceGenerator(name = "COMENTARIO_SEQUENCIA", sequenceName = "SEQ_COMENTARIO", allocationSize = 1)
    @Column(name = "id_comentario")
    private Integer idComentario;

    @Column(name = "id_atividade", insertable = false, updatable = false)
    private Integer idAtividade;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "situacao")
    private TipoAvaliacao situacao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ATIVIDADE", referencedColumnName = "ID_ATIVIDADE")
    private AtividadeEntity atividade;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID_ALUNO")
    private AlunoEntity aluno;
}
