package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.VagaEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {

    List<VagaEntity> findBySituacao(Situacao situacao);

    Page<VagaEntity> findAllByNomeContainingIgnoreCase(Pageable pageable, String nome);

    Optional<VagaEntity> findByNome(String nome);

}
