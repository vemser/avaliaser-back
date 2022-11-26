package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
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





}
