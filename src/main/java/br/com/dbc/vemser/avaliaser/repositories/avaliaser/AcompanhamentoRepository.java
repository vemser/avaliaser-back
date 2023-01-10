package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<AcompanhamentoEntity, Integer> {
    Page<AcompanhamentoEntity> findAllByAtivo(Ativo ativo,Pageable pageable);

//    @Query(" SELECT obj " +
//            " FROM  Acompanhamento obj" +
//            " LEFT JOIN obj.programa prog" +
//            " WHERE (:nome is null or UPPER(prog.nome) LIKE UPPER(:nome))")
//    Page<AcompanhamentoEntity> findAllByProgramaNome(String nome, Pageable pageable);

    Page<AcompanhamentoEntity> findAllByPrograma_NomeLikeIgnoreCaseAndAtivo(String nome,Ativo ativo, Pageable pageable);

    Page<AcompanhamentoEntity> findAllByTituloLikeIgnoreCaseAndAtivo(String titulo, Ativo ativo, Pageable pageable);

}
