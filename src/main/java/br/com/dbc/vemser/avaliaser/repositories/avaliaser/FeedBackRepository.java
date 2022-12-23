package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {
    Page<FeedBackEntity> findAll(Pageable pageable);
    Page<FeedBackEntity> findAllByIdAluno(Integer idAluno, Pageable pageable);
}
