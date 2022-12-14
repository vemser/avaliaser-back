package br.com.dbc.vemser.avaliaser.entities;


import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoVagaPrograma;
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
@Entity(name = "Vaga")
public class VagaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_SEQ")
    @SequenceGenerator(name = "VAGA_SEQ", sequenceName = "seq_vaga", allocationSize = 1)
    @Column(name = "id_vaga")
    private Integer idVaga;

    @Column(name = "id_cliente", insertable = false, updatable = false)
    private Integer idCliente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "quantidade_vagas")
    private Integer quantidade;

    @Column(name = "quantidade_alocados")
    private Integer quantidadeAlocados = 0;

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDate dataFechamento;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private SituacaoVagaPrograma situacaoVagaPrograma;

    @Column(name = "ativo")
    @Enumerated(EnumType.ORDINAL)
    private Ativo ativo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteEntity cliente;


    @JsonIgnore
    @OneToMany(mappedBy = "vaga", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReservaAlocacaoEntity> resevasAlocacoes;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Vaga_Programa",
            joinColumns = @JoinColumn(name = "id_vaga"),
            inverseJoinColumns = @JoinColumn(name = "id_programa")
    )
    private Set<ProgramaEntity> programa = new HashSet<>();
}
