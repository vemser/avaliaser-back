package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.ProgramaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
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
    Page<ProgramaEntity> findAllByAtivo(Ativo ativo, Pageable pageable);
    Optional<ProgramaEntity> findByIdProgramaAndAtivo(Ativo ativo, Integer id);
    Optional<ProgramaEntity> findAllByAtivo(Ativo ativo);
    Page<ProgramaEntity> findAllByNomeContainingIgnoreCaseAndAtivo(Ativo ativo, String nome, Pageable pageable);
}
