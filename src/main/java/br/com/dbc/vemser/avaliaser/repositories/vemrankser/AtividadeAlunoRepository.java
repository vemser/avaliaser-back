package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.AtividadeAlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.Set;

public interface AtividadeAlunoRepository extends JpaRepository<AtividadeAlunoEntity, Integer> {

    Optional<AtividadeAlunoEntity> findByAluno_IdAlunoAndAtividade_IdAtividade(Integer idAluno, Integer idAtividade);
    Page<AtividadeAlunoEntity> findByAluno_IdAlunoAndSituacao(PageRequest pageable, Integer idAluno, Situacao situacao);
}
