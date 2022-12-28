package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "ATIVIDADE")
public class AtividadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATIVIDADE_SEQUENCIA")
    @SequenceGenerator(name = "ATIVIDADE_SEQUENCIA", sequenceName = "SEQ_ATIVIDADE", allocationSize = 1)
    @Column(name = "ID_ATIVIDADE")
    private Integer idAtividade;

//    @Column(name = "id_modulo", insertable = false, updatable = false)
//    private Integer idModulo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "nome_instrutor")
    private String nomeInstrutor;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private Tipo situacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "peso_atividade")
    private Integer pesoAtividade;

    @Column(name = "link")
    private String link;

    @Column(name = "id_programa", insertable = false, updatable = false)
    private Integer idPrograma;

    @JsonIgnore
    @OneToMany(mappedBy = "atividade", fetch = FetchType.LAZY)
    private Set<ComentarioEntity> comentarios;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    private ProgramaEntity programa;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo")
    private Set<ModuloAtividadeEntity> modulos = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "atividade")
    private Set<AtividadeAlunoEntity> atividadeAlunos;

}
