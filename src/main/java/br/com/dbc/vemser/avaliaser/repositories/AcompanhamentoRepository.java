package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<AcompanhamentoEntity, Integer> {

    Page<AcompanhamentoEntity> findAll(Pageable pageable);

}
