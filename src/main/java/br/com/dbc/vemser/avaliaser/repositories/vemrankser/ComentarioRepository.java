package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.ComentarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Integer> {
    List<ComentarioEntity> findAllByIdAtividade(Integer idAtividade);

//    List<ComentarioEntity> findAllByStatusComentario(String statusComentario);
    List<ComentarioEntity> findAllBySituacao(String situacao);

    Page<ComentarioEntity> findAllByIdUsuario(Pageable pageable,Integer idAluno);

}
