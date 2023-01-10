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

    @Query("SELECT obj " +
            "FROM FEEDBACK_MODULO obj " +
            "LEFT JOIN obj.feedBack fb " +
            "LEFT JOIN fb.alunoEntity aluno " +
            "LEFT JOIN aluno.trilha trilha " +
            "WHERE (fb.ativo = 1)" +
            "AND ( aluno.idAluno = :idAluno or :idAluno is null) " +
            "AND ( trilha.idTrilha = :idTrilha or :idTrilha is null) " +
            "AND ( fb.situacao = :situacao or :situacao is null)" +
            "AND (:nomeInstrutor is null or UPPER(fb.nomeInstrutor) LIKE UPPER(concat(:nomeInstrutor, '%')))" )
    Page<FeedBackModuloEntity> findByFiltro(Integer idAluno, Integer idTrilha, TipoAvaliacao situacao, String nomeInstrutor, Pageable pageable);

}
