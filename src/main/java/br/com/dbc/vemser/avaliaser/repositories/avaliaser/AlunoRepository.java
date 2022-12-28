package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {
    //    Optional<AlunoEntity> findByAtivoAndIdAluno(Ativo ativo, Integer idUsuario);
//
    Page<AlunoEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllByNomeContainingIgnoreCaseAndAtivo(String nome, Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllByIdAlunoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllByEmailContainingIgnoreCaseAndAtivo(String email, Ativo ativo, Pageable pageable);

    Optional<AlunoEntity> findByEmailContainingIgnoreCase(String email);
}
