package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.dto.avaliacao.AvaliacaoDTO;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {

    Page<AvaliacaoEntity> findAllByIdAluno(Integer idAluno, Pageable pageable);

}
