package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.VagaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {

    Page<VagaEntity> findBySituacaoAndAtivo(Pageable pageable, Situacao situacao, Ativo ativo);

    Page<VagaEntity> findByIdVagaAndAtivo(Pageable pageable, Integer id, Ativo ativo);

    Page<VagaEntity> findAllByAtivo(Pageable pageable, Ativo ativo);

    Page<VagaEntity> findAllByNomeContainingIgnoreCaseAndAtivo(Pageable pageable, String nome, Ativo ativo);

    Optional<VagaEntity> findByNomeAndAtivo(String nome, Ativo ativo);

    Optional<VagaEntity> findByIdVagaAndAtivo(Integer id, Ativo ativo);

}
