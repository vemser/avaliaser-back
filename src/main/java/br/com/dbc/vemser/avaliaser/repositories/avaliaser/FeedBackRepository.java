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
import java.util.Set;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {
    Optional<FeedBackEntity> findByIdFeedBackAndAtivo(Integer idAluno, Ativo ativo);

    Page<FeedBackEntity> findAllByAtivo(Pageable pageable, Ativo ativo);

    Page<FeedBackEntity> findAllByIdAlunoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<FeedBackEntity> findByIdFeedBackAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<FeedBackEntity> findAllByAlunoEntity_NomeContainingIgnoreCaseAndAtivo(String nome, Ativo ativo, Pageable pageable);

    Set<FeedBackEntity> findAllByNomeInstrutorContainsIgnoreCase(String nome);

    @Query("SELECT obj " +
            "FROM Feedback obj " +
            "LEFT JOIN obj.alunoEntity aluno " +
            "LEFT JOIN obj.moduloEntity modulo " +
            "LEFT JOIN modulo.trilha trilha " +
            "WHERE ( aluno.idAluno = :idAluno or :idAluno is null) " +
            "AND ( trilha.idTrilha = :idTrilha or :idTrilha is null) " +
            "AND ( obj.situacao = :situacao or :situacao is null)" +
            "AND ( obj.nomeInstrutor = :nomeInstrutor or :nomeInstrutor is null)" )
    Page<FeedBackEntity> findByFiltro(Integer idAluno, Integer idTrilha, TipoAvaliacao situacao, String nomeInstrutor, Pageable pageable);

}
