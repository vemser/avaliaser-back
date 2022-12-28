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

    Optional<ClienteEntity> findByEmailIgnoreCaseAndAtivo(Ativo ativo,String email);
    Page<ClienteEntity> findAllByAtivo(Ativo ativo, Pageable pageable);
    Page<ClienteEntity> findAllByIdAlunoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);
    Page<ClienteEntity> findAllByEmailContainingIgnoreCaseAndAtivo(Ativo ativo,Pageable pageable, String email);
    Page<ClienteEntity> findAllByNomeContainingIgnoreCaseAndAtivo(Ativo ativo, Pageable pageable, String nome);
}
