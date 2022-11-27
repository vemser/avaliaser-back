package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Avaliacao")
public class AvaliacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVALIACAO_SEQUENCIA")
    @SequenceGenerator(name = "AVALIACAO_SEQUENCIA", sequenceName = "SEQ_AVALIACAO", allocationSize = 1)
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;

    @Column(name = "id_aluno", insertable = false, updatable = false)
    private Integer idAluno;

    @Column(name = "id_acompanhamento", insertable = false, updatable = false)
    private Integer idAcompanhamento;

    @Column(name = "tipo")
    private Tipo tipo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "Data_Criacao")
    private LocalDate dataCriacao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acompanhamento", referencedColumnName = "id_acompanhamento")
    @ToString.Exclude
    private AcompanhamentoEntity acompanhamentoEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    @ToString.Exclude
    private AlunoEntity alunoEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ToString.Exclude
    private UsuarioEntity usuarioEntity;

}
