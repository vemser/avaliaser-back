package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.ReservaAlocacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaAlocacaoRepository extends JpaRepository<ReservaAlocacaoEntity, Integer> {

    @Query("SELECT obj " +
            "FROM Reserva_Alocacao obj " +
            "LEFT JOIN obj.aluno a " +
            "LEFT JOIN obj.vaga v " +
            "WHERE (:nomeAluno is null or UPPER(a.nome) LIKE UPPER(:nomeAluno)) OR (:nomeVaga is null or UPPER(v.nome) LIKE UPPER(:nomeVaga)) " +
            "ORDER BY a.nome")
    Page<ReservaAlocacaoEntity> findAllByFiltro(Pageable pageable, String nomeAluno, String nomeVaga);
}