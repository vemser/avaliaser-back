package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.ClienteEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    ClienteEntity findByEmailIgnoreCaseAndAtivo(String email, Ativo ativo);
    Page<ClienteEntity> findAllByAtivo(Ativo ativo, Pageable pageable);
//    Page<ClienteEntity> findAllByIdClienteAndAtivo(Integer idCliente, Ativo ativo, Pageable pageable);
    Page<ClienteEntity> findAllByEmailContainingIgnoreCaseAndAtivo(String email, Ativo ativo, Pageable pageable);
    Page<ClienteEntity> findAllByNomeContainingIgnoreCaseAndAtivo(String email, Ativo ativo, Pageable pageable);
}
