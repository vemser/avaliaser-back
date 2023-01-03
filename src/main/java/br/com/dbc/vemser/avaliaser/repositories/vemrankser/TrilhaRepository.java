package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrilhaRepository extends JpaRepository<TrilhaEntity, Integer> {


    Page<TrilhaEntity> findAllByNomeContainingIgnoreCaseAndAtivo(String nome, Pageable pageable, Ativo ativo);

    Page<TrilhaEntity> findByIdTrilhaAndAtivo(Integer idTrilha, Pageable pageable, Ativo ativo);

    Page<TrilhaEntity> findAllByAtivo(Pageable pageable, Ativo ativo);

    Optional<TrilhaEntity> findByIdTrilhaAndAtivo(Integer id, Ativo ativo);


}
