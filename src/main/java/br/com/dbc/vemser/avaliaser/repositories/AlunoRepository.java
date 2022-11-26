package br.com.dbc.vemser.avaliaser.repositories;

import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {
}
