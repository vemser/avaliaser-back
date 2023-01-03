package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Integer> {

    Optional<AtividadeEntity> findByIdAtividadeAndAtivo(Integer id, Ativo ativo);
    Page<AtividadeEntity> findAllByAtivo(Ativo ativo, Pageable pageable);


}
