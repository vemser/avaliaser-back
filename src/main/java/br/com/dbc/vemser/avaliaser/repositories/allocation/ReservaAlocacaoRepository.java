package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.ReservaAlocacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaAlocacaoRepository extends JpaRepository<ReservaAlocacaoEntity, Integer> {

    Page<ReservaAlocacaoEntity> findAllByAluno_NomeContainingIgnoreCaseOrVaga_Cliente_NomeContainingIgnoreCaseOrVaga_NomeContainingIgnoreCase(String nomeAluno, String nomeCliente, String nomeVaga, Pageable pageable);
}