package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
}
