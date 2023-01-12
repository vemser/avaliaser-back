package br.com.dbc.vemser.avaliaser.repositories.avaliaser;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.entities.FeedBackModuloEntity;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedBackModuloRepository extends JpaRepository<FeedBackModuloEntity, Integer> {

}
