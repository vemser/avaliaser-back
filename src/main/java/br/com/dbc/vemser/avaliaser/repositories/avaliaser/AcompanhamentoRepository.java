package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<AcompanhamentoEntity, Integer> {
    Page<AcompanhamentoEntity> findAll(Pageable pageable);

}