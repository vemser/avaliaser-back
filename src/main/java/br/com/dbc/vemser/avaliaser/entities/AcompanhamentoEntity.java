package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Acompanhamento")
public class AcompanhamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACP_SEQUENCIA")
    @SequenceGenerator(name = "ACP_SEQUENCIA", sequenceName = "SEQ_ACOMPANHAMENTO", allocationSize = 1)
    @Column(name = "id_acompanhamento")
    private Integer idAcompanhamento;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Ativo ativo;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

//    @Column(name = "id_programa", updatable = false, insertable = false)
//    private Integer idPrograma;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acompanhamentoEntity")
    private Set<AvaliacaoEntity> avaliacoes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    private ProgramaEntity programa;

}
