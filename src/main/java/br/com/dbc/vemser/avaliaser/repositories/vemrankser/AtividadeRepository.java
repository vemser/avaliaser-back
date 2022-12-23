package br.com.dbc.vemser.avaliaser.repositories.vemrankser;

import br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeNotaPageDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeTrilhaDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Integer> {

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeTrilhaDTO ( " +
            " a.idAtividade, " +
            " a.nomeInstrutor, " +
            " a.titulo, " +
            " a.pesoAtividade, " +
            " a.dataCriacao, " +
            " a.dataEntrega, " +
            " atr.nome, " +
            " atr.edicao, " +
            " atr.anoEdicao " +
            " ) " +
            " from ATIVIDADE a " +
            " inner join a.trilhas atr " +
            " on (atr.idTrilha = :idTrilha or :idTrilha is null) " +
            " where (a.statusAtividade = :atividadeStatus or :atividadeStatus is null ) " +
            " ")
    Page<AtividadeTrilhaDTO> listarAtividadePorStatus(Pageable pageable, Integer idTrilha, AtividadeStatus atividadeStatus);

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO ( " +
            " a.idAtividade, " +
            " a.nomeInstrutor, " +
            " a.titulo, " +
            " a.instrucoes, " +
            " a.pesoAtividade, " +
            " a.dataCriacao, " +
            " a.dataEntrega, " +
            " a.idModulo, " +
            " a.statusAtividade, " +
            " a.modulo.nome, " +
            " atr.nome, " +
            " atr.edicao " +
            " ) " +
            " from ATIVIDADE a " +
            " inner join a.trilhas atr " +
            " where (atr.idTrilha = :idTrilha or :idTrilha is null ) " +
            " ")
    Page<AtividadeMuralDTO> listarAtividadeMuralInstrutor(Pageable pageable, Integer idTrilha);


    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO ( " +
            " au.idAtividade, " +
            " u.idUsuario, " +
            " au.titulo, " +
            " au.instrucoes, " +
            " au.pesoAtividade, " +
            " au.dataCriacao, " +
            " au.dataEntrega, " +
            " au.idModulo, " +
            " au.statusAtividade, " +
            " u.nome, " +
            " atr.nome, " +
            " atr.edicao " +
            " ) " +
            " from USUARIO u " +
            " inner join u.atividades au " +
            " inner join au.trilhas atr " +
            " on ( u.idUsuario = :idUsuario or :idUsuario is null ) " +
            " where ( au.statusAtividade = :atividadeStatus or :atividadeStatus is null) " +
            "  ")
    Page<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer idUsuario, AtividadeStatus atividadeStatus,Pageable pageable);


    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AtividadeNotaPageDTO ( " +
            " u.idUsuario, " +
            " u.nome, " +
            " au.idAtividade, " +
            " au.pontuacao, " +
            " aul.link " +
            " ) " +
            " from USUARIO u " +
            " left join u.trilhas ut " +
            " left join ut.modulos utm" +
            " left join u.atividades au" +
            " left join au.links aul " +
            " on ( au.idModulo = :idModulo and ut.idTrilha = :idTrilha and au.statusAtividade = :atividadeStatus ) " +
            "  ")
    Page<AtividadeNotaPageDTO> listarAtividadePorIdTrilhaIdModulo(Pageable pageable, Integer idTrilha, Integer idModulo, AtividadeStatus atividadeStatus);

}
