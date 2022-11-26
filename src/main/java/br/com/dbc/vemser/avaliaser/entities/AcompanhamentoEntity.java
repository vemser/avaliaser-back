package br.com.dbc.vemser.avaliaser.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Acompanhamento")
public class AcompanhamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACP_SEQUENCIA")
    @SequenceGenerator(name = "ACP_SEQUENCIA", sequenceName = "SEQ_ACP", allocationSize = 1)
    @Column(name = "id_acompanhamento")
    private Integer idAcompanhamento;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acompanhamentoEntity")
    private Set<AvaliacaoEntity> avaliacoes;

}
