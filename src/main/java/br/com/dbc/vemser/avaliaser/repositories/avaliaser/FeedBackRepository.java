package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {
    Optional<FeedBackEntity> findByIdFeedBackAndAtivo(Integer idAluno, Ativo ativo);
    Page<FeedBackEntity> findAllByAtivo(Pageable pageable, Ativo ativo);

    Page<FeedBackEntity> findAllByIdAlunoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<FeedBackEntity> findByIdFeedBackAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<FeedBackEntity> findAllByAlunoEntity_NomeContainingIgnoreCaseAndAtivo(String nome, Ativo ativo, Pageable pageable);
}
