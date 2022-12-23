package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.vemrankser.ranqueamento.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity,Integer> {
}
