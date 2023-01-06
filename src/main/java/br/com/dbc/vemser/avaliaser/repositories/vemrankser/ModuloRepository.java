package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<ModuloEntity, Integer> {

    Page<ModuloEntity> findAllByNomeContainingIgnoreCase(Pageable pageable, String nome);
    Page<ModuloEntity> findAllByIdModulo(Pageable pageable, Integer id);
    Optional<ModuloEntity> findByIdModuloAndAtivo(Integer idModulo, Ativo ativo);
    Page<ModuloEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

}

