package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {
    Page<FeedBackEntity> findAll(Pageable pageable);
    @Query("select new br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackDTO(" +
            " f.idAluno, " +
            " f.idUsuario, " +
            " f.idFeedBack, " +
            " f.descricao, " +
            " f.tipo " +
            ")" +
            " from Feedback f " +
            " left join f.alunoEntity a " +
            " left join f.usuarioEntity u " +
            " where (:idAluno is null or f.idAluno = :idAluno)")
    Page<FeedBackEntity> findAllByIdAluno(Integer idAluno, Pageable pageable);
}
