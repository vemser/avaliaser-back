package br.com.dbc.vemser.avaliaser.repositories.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {
    //    Optional<AlunoEntity> findByAtivoAndIdAluno(Ativo ativo, Integer idUsuario);
//
    Page<AlunoEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllBySituacao(Pageable pageable, SituacaoReserva situacao);

    Page<AlunoEntity> findAllByNomeContainingIgnoreCaseAndAtivo(String nome, Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllByIdAlunoAndAtivo(Integer idAluno, Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllByEmailContainingIgnoreCaseAndAtivo(String email, Ativo ativo, Pageable pageable);

    Page<AlunoEntity> findAllByTrilha_IdTrilhaInAndProgramaIdProgramaAndAtivo(List<Integer> idTrilha, Integer idPrograma, Ativo ativo, Pageable pageable);

//    Optional<AlunoEntity> findByEmailContainingIgnoreCase(String email);


    Optional<AlunoEntity> findByEmail(String email);


}
