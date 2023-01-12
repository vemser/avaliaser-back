package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {
    Optional<FeedBackEntity> findByIdFeedBackAndAtivo(Integer idAluno, Ativo ativo);

    Page<FeedBackEntity> findAllByAtivo(Pageable pageable, Ativo ativo);

    Page<FeedBackEntity> findAllByIdAlunoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<FeedBackEntity> findByIdFeedBackAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<FeedBackEntity> findAllByAlunoEntity_NomeContainingIgnoreCaseAndAtivo(String nome, Ativo ativo, Pageable pageable);


    @Query(" SELECT obj " +
            " from Feedback obj " +
            " LEFT JOIN obj.moduloEntity m" +
            " LEFT JOIN m.trilha t" +
            " WHERE (:nomeAluno is null or UPPER(obj.alunoEntity.nome) LIKE UPPER(concat('%', :nomeAluno, '%'))) AND " +
            " (:nome is null or UPPER(t.nome) LIKE UPPER(concat('%', :nome, '%'))) AND " +
            " (:nomeInstrutor is null or UPPER(obj.nomeInstrutor) LIKE UPPER(concat('%', :nomeInstrutor, '%'))) AND " +
            " (:situacao is null or obj.situacao = :situacao) AND " +
            " (:ativo = obj.ativo)"
    )
    Page<FeedBackEntity> findByFiltro(String nomeAluno, String nome, String nomeInstrutor, TipoAvaliacao situacao, Ativo ativo, Pageable pageable);
}
