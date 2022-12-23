package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByEmailIgnoreCase(String email);

    Page<ClienteEntity> findAllByEmailContainingIgnoreCase(Pageable pageable, String email);

    Page<ClienteEntity> findAllByNomeContainingIgnoreCase(Pageable pageable, String nome);
}
