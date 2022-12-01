package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByEmailAndAtivo(String email, Ativo ativo);

    Optional<UsuarioEntity> findByAtivoAndIdUsuario(Ativo ativo, Integer idUsuario);

    Page<UsuarioEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

    List<UsuarioDTO> findAllByCargoAndAtivo(Cargo cargo, Ativo ativo);

}
