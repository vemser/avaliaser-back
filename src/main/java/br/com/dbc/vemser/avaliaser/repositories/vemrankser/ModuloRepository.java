package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuloRepository extends JpaRepository<ModuloEntity, Integer> {

    Optional<ModuloEntity> findByIdModuloAndAtivo(Integer idModulo, Ativo ativo);
    Page<ModuloEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

}

