package br.com.dbc.vemser.avaliaser.repositories.allocation;


import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface TecnologiaRepository extends JpaRepository<TecnologiaEntity, Integer> {
    Page<TecnologiaEntity> findByNomeIsLikeIgnoreCase(String nomeTecnologia, Pageable pageable);

    Set<TecnologiaEntity> findAllByNomeIn(List<String> tecnologias);

    Set<TecnologiaEntity> findAllByAlunos(AlunoEntity aluno);

    TecnologiaEntity findByNome(String nome);
}
