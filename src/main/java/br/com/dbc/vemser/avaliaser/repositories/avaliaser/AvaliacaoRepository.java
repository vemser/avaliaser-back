package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {
//    Page<AvaliacaoEntity> findAllByIdAvaliacaoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);
    Page<AvaliacaoEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

    Page<AvaliacaoEntity> findAllByAcompanhamentoEntity_IdAcompanhamentoOOrAcompanhamentoEntity_TituloContainingIgnoreCaseOrIdAlunoOrAcompanhamentoEntity_Ativo(Integer idAcompanhamento,
                                                                                                                                          String tituloAcompanhamento,
                                                                                                                                          Integer idAluno,
                                                                                                                                          Ativo ativo,
                                                                                                                                          Pageable pageable);


}
