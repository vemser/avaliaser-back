package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {
}
