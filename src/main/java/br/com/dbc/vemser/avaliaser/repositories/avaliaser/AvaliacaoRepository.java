package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.AcompanhamentoEntity;
import br.com.dbc.vemser.avaliaser.entities.AvaliacaoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.TipoAvaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {
//    Page<AvaliacaoEntity> findAllByIdAvaliacaoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);
    Page<AvaliacaoEntity> findAllByAtivo(Ativo ativo, Pageable pageable);
    Page<AvaliacaoEntity> findByIdAvaliacaoAndAtivo(Integer id, Ativo ativo, Pageable pageable);
    Page<AvaliacaoEntity> findAllByAcompanhamentoEntity_TituloContainingIgnoreCaseAndAtivo(String nome,Ativo ativo, Pageable pageable);
    Page<AvaliacaoEntity> findAllByAlunoEntity_NomeContainingIgnoreCaseAndAtivo(String nome,Ativo ativo, Pageable pageable);

    Page<AvaliacaoEntity> findAllByTipoAvaliacaoAndAtivo(TipoAvaliacao tipoAvaliacao, Ativo ativo, Pageable pageable);

//    Page<AvaliacaoEntity> findAllByAcompanhamentoEntity_IdAcompanhamentoOOrAcompanhamentoEntity_TituloContainingIgnoreCaseOrIdAlunoOrAcompanhamentoEntity_Ativo(Integer idAcompanhamento,
//                                                                                                                                          String tituloAcompanhamento,
//                                                                                                                                          Integer idAluno,
//                                                                                                                                          Ativo ativo,
//                                                                                                                                          Pageable pageable);


}
