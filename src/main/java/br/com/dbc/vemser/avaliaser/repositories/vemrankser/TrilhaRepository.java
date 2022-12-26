//package br.com.dbc.vemser.avaliaser.repositories.vemrankser;
//
//import br.com.vemrankser.ranqueamento.dto.TrilhaDTO;
//import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface TrilhaRepository extends JpaRepository<TrilhaEntity, Integer> {
//
//
//    Page<TrilhaEntity> findAllByIdTrilha(Integer idTrilha, Pageable pageable);
//
//    Optional<TrilhaEntity> findByEdicao(TrilhaDTO edicao);
//
//    Optional<TrilhaEntity> findByNomeIgnoreCase(String nome);
//
//    List<TrilhaEntity> findAllByNomeContainingIgnoreCase(String nome);
//
//    List<TrilhaEntity> findAllByEdicao(Integer edicao);
//}
