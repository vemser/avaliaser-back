package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.TrilhaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrilhaRepository extends JpaRepository<TrilhaEntity, Integer> {


    Page<TrilhaEntity> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<TrilhaEntity> findByIdTrilha(Integer idTrilha, Pageable pageable);

    @Query(value = "select t from Trilha t where t.ativo = 1 " , countQuery = "select count(t)  from Trilha t where t.ativo = 1")
    Page<TrilhaEntity> findAllTrilhasAtiva(Pageable pageable);


}
