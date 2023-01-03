package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramaRepository extends JpaRepository<ProgramaEntity, Integer> {

    @Query(value = "select p from Programa p where p.situacao = 3 " , countQuery = "select count(p)  from Programa p where p.situacao = 3")
    Page<ProgramaEntity> findAllProgramasAbertos(Pageable pageable);

    Page<ProgramaEntity> findAllByNomeContainingIgnoreCaseAndSituacao(String nome, Pageable pageable, Situacao situacao);

    ProgramaEntity findByIdProgramaAndSituacao(Integer idPrograma, Situacao situacao);
}
