package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.AtividadeAlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AtividadeAlunoRepository extends JpaRepository<AtividadeAlunoEntity, Integer> {

    Optional<AtividadeAlunoEntity> findByAluno_IdAlunoAndAtividade_IdAtividade(Integer idAluno, Integer idAtividade);
    Page<AtividadeAlunoEntity> findByAluno_IdAlunoAndSituacao(PageRequest pageable, Integer idAluno, Situacao situacao);

    @Query("SELECT obj " +
            "FROM ATIVIDADE_ALUNO obj " +
            "LEFT JOIN obj.atividade atv " +
            "LEFT JOIN obj.aluno aluno " +
            "LEFT JOIN atv.modulos m " +
            "WHERE ( m.idModulo = :idModulo or :idModulo is null) " +
            "AND ( aluno.idAluno = :idAluno or :idAluno is null) " +
            "AND ( atv.idAtividade = :idAtividade or :idAtividade is null)" )
    Page<AtividadeAlunoEntity> findByFiltro(Integer idModulo, Integer idAluno, Integer idAtividade, Pageable pageable);

}
