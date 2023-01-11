package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<AcompanhamentoEntity, Integer> {
    Page<AcompanhamentoEntity> findAllByAtivo(Ativo ativo,Pageable pageable);

    Page<AcompanhamentoEntity> findByIdAcompanhamentoAndAtivo(Integer id, Ativo ativo,Pageable pageable);
    Page<AcompanhamentoEntity> findAllByPrograma_NomeContainingIgnoreCaseAndAtivo(String nome,Ativo ativo, Pageable pageable);

    Page<AcompanhamentoEntity> findAllByTituloContainingIgnoreCaseAndAtivo(String titulo, Ativo ativo, Pageable pageable);

}
