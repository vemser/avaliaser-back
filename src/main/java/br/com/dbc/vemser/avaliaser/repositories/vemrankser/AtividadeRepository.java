package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.ReservaAlocacaoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Integer> {

    Optional<AtividadeEntity> findByIdAtividadeAndAtivo(Integer id, Ativo ativo);
    Page<AtividadeEntity> findAllByAtivo(Ativo ativo, Pageable pageable);
    Page<AtividadeEntity> findAllByModulosContaining(ModuloEntity idModulo, Pageable pageable);
    Page<AtividadeEntity> findAllByAlunosContaining(AlunoEntity idAluno, Pageable pageable);
    Page<AtividadeEntity> findAllByTituloContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT obj " +
            "FROM ATIVIDADE obj " +
            "JOIN MODULO_ATIVIDADE ma ON ma.idModuloAtividade.idAtividade = obj.idAtividade " +
            "WHERE ma.idModuloAtividade.idModulo = :modulo"
    )
    Page<AtividadeEntity> findAtividadeEntitiesByModuloId(Integer modulo, Pageable pageable);


}
