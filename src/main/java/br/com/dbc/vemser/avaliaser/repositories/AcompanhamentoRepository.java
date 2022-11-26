package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<AcompanhamentoEntity, Integer> {
}
