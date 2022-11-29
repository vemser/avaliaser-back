package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {

    Optional<AlunoEntity> findByEmailAndAtivo(Ativo ativo, String email);

    Optional<AlunoEntity> findByAtivoAndIdAluno(Ativo ativo, Integer idUsuario);

    Page<AlunoEntity> findAllByAtivo(Ativo ativo, Pageable pageable);
}
