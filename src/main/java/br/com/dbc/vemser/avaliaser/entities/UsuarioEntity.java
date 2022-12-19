package br.com.dbc.vemser.avaliaser.entities;

import br.com.dbc.vemser.avaliaser.enums.Ativo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQUENCIA")
    @SequenceGenerator(name = "USUARIO_SEQUENCIA", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_cargo", insertable = false, updatable = false)
    private Integer idCargo;

    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "senha")
    private String senha;

    @Lob
    @Column(name = "foto")
    private byte[] image;
    @Column(name = "ativo")
    @Enumerated(EnumType.STRING)
    private Ativo ativo;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioEntity", cascade = CascadeType.REMOVE)
    private Set<FeedBackEntity> feedBack; // FIXME serializable

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioEntity", cascade = CascadeType.REMOVE)
    private Set<AvaliacaoEntity> avaliacoes;
    // FIXME serializable!
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    private CargoEntity cargo;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<CargoEntity> cargos = new HashSet<>();
        cargos.add(cargo);
        return cargos;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo.ordinal() == 1;
    }
}
