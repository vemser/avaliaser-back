package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.AtividadeAlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtividadeAlunoRepository extends JpaRepository<AtividadeAlunoEntity, Integer> {

    Optional<AtividadeAlunoEntity> findByAluno_IdAlunoAndAtividade_IdAtividade(Integer idAluno, Integer idAtividade);
}
