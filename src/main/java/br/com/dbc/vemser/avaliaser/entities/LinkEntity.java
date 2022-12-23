package br.com.dbc.vemser.avaliaser.entities;

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
@Entity(name = "LINK")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LINK_SEQUENCIA")
    @SequenceGenerator(name = "LINK_SEQUENCIA", sequenceName = "SEQ_LINK", allocationSize = 1)
    @Column(name = "id_link")
    private Integer idLink;

    @Column(name = "link")
    private String link;

    @Column(name = "id_atividade", insertable = false, updatable = false)
    private Integer idAtividade;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atividade",referencedColumnName = "id_atividade")
    private AtividadeEntity atividade;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;


}
