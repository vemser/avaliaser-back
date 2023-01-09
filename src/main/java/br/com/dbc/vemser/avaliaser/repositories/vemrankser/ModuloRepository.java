package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import org.bouncycastle.math.raw.Mod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<ModuloEntity, Integer> {

    Page<ModuloEntity> findAllByNomeContainingIgnoreCaseAndAtivo(Pageable pageable, String nome, Ativo ativo);
    Page<ModuloEntity> findAllByIdModuloAndAtivo(Pageable pageable, Integer id, Ativo ativo);
    Optional<ModuloEntity> findByIdModuloAndAtivo(Integer idModulo, Ativo ativo);
    Page<ModuloEntity> findAllByAtivo(Ativo ativo, Pageable pageable);

}

