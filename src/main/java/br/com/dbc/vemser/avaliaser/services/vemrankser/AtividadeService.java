package br.com.dbc.vemser.avaliaser.services.vemrankser;


import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadedto.*;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadeentregardto.AtividadeEntregaCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadeentregardto.AtividadeEntregaDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadepagedto.AtividadePaginacaoDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeAlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.AtividadeEntity;
import br.com.dbc.vemser.avaliaser.entities.ModuloEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoAtividade;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.AtividadeAlunoRepository;
import br.com.dbc.vemser.avaliaser.repositories.vemrankser.AtividadeRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final AtividadeAlunoRepository atividadeAlunoRepository;
    private final ModuloService moduloService;
    private final AlunoService alunoService;
    private final ProgramaService programaService;
    private final ObjectMapper objectMapper;


    public PageDTO<AtividadeMuralAlunoDTO> listarAtividadePorStatus(Integer pagina, Integer tamanho, String email, SituacaoAtividade situacao) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size n??o poder ser menor que zero.");
        }
        if (tamanho > 0) {
        if (!email.contains("@")) {
            email += "@dbccompany.com.br";
        }
        AlunoDTO alunoDTO = alunoService.findByEmail(email);
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeAlunoEntity> atividadeAlunoEntities = atividadeAlunoRepository.findByAluno_AtivoAndAluno_IdAlunoAndSituacao(pageRequest, Ativo.S, alunoDTO.getIdAluno(), situacao);

        List<AtividadeMuralAlunoDTO> atividadeMuralAlunoDTOS = atividadeAlunoEntities.getContent()
                .stream()
                .map(atividadeAlunoEntity -> {
                    AtividadeEntity atividadeEntity = atividadeAlunoEntity.getAtividade();
                    AtividadeMuralAlunoDTO atividadeMuralAlunoDTO = objectMapper.convertValue(atividadeEntity, AtividadeMuralAlunoDTO.class);
                    atividadeMuralAlunoDTO.setDataEntregaAluno(atividadeAlunoEntity.getDataEntrega());
                    atividadeMuralAlunoDTO.setDataEntregaLimite(atividadeEntity.getDataEntrega());
                    atividadeMuralAlunoDTO.setLink(atividadeAlunoEntity.getLink());
                    atividadeMuralAlunoDTO.setSituacao(atividadeAlunoEntity.getSituacao());
                    atividadeMuralAlunoDTO.setNota(atividadeAlunoEntity.getNota());
                    atividadeMuralAlunoDTO.setPrograma(objectMapper.convertValue(atividadeEntity.getPrograma(), ProgramaDTO.class));

                    return atividadeMuralAlunoDTO;
                })
                .toList();

        return new PageDTO<>(atividadeAlunoEntities.getTotalElements(),
                atividadeAlunoEntities.getTotalPages(),
                pagina,
                tamanho,
                atividadeMuralAlunoDTOS);
        }
        List<AtividadeMuralAlunoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }
    public AtividadePaginacaoDTO<AtividadeDTO> listarAtividades(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (pagina < 0 || tamanho < 0) {
            throw new RegraDeNegocioException("Page ou size n??o poder ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<AtividadeEntity> atividadeEntity = atividadeRepository.findAllByAtivo(Ativo.S, pageRequest);
            List<AtividadeDTO> atividadeDTOList = atividadeEntity.getContent()
                    .stream()
                    .map(this::converterAtividadeDTO)
                    .toList();

            return new AtividadePaginacaoDTO<>(atividadeEntity.getTotalElements(), atividadeEntity.getTotalPages(), pagina, tamanho, atividadeDTOList);
        }
        List<AtividadeDTO> listaVazia = new ArrayList<>();
        return new AtividadePaginacaoDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public AtividadeDTO createAtividade(AtividadeCreateDTO atividadeCreateDTO) throws RegraDeNegocioException {

        AtividadeEntity atividadeEntity = objectMapper.convertValue(atividadeCreateDTO, AtividadeEntity.class);
        atividadeEntity.setPrograma(programaService.findByIdAtivo(atividadeCreateDTO.getIdPrograma()));
//        atividadeEntity.setSituacao(Situacao.ABERTO);
        atividadeEntity.setAtivo(Ativo.S);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        atividadeEntity.setDataCriacao(now);
        verificarDatas(atividadeCreateDTO, atividadeEntity);
        atividadeEntity.setDataEntrega(atividadeCreateDTO.getDataEntrega());

        for (Integer modulos : atividadeCreateDTO.getModulos()) {
            atividadeEntity.getModulos().add(moduloService.buscarPorIdModulo(modulos));
        }
        for (Integer alunos : atividadeCreateDTO.getAlunos()) {
            atividadeEntity.getAlunos().add(alunoService.findById(alunos));
        }
        atividadeEntity = atividadeRepository.save(atividadeEntity);

        for (Integer alunos : atividadeCreateDTO.getAlunos()) {
            AtividadeAlunoEntity atividadeAlunoEntity = atividadeAlunoRepository.findByAluno_IdAlunoAndAtividade_IdAtividade(alunos, atividadeEntity.getIdAtividade()).get();
            atividadeAlunoEntity.setSituacao(SituacaoAtividade.PENDENTE);
            atividadeAlunoRepository.save(atividadeAlunoEntity);
        }

        return converterAtividadeDTO(atividadeEntity);
    }

    public AtividadeEntregaDTO entregarAtividade(AtividadeEntregaCreateDTO atividadeEntregaCreateDTO) throws RegraDeNegocioException {
        AlunoDTO aluno = alunoService.findByEmail(atividadeEntregaCreateDTO.getEmail());
        AtividadeAlunoEntity atividadeAlunoEntity = atividadeAlunoRepository.findByAluno_IdAlunoAndAtividade_IdAtividade(aluno.getIdAluno(), atividadeEntregaCreateDTO.getIdAtividade())
                .orElseThrow(() -> new RegraDeNegocioException("Atividade n??o encontrada."));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        atividadeAlunoEntity.setNota(0);
        atividadeAlunoEntity.setLink(atividadeEntregaCreateDTO.getLink());
        atividadeAlunoEntity.setDataEntrega(now);
        atividadeAlunoEntity.setSituacao(SituacaoAtividade.ENTREGUE);

        atividadeAlunoRepository.save(atividadeAlunoEntity);

        return objectMapper.convertValue(atividadeAlunoEntity, AtividadeEntregaDTO.class);
    }

    public AtividadeDTO atualizarAtividade(Integer idAtividade, AtividadeCreateDTO atividadeAtualizar) throws RegraDeNegocioException {
        AtividadeEntity atividadeRecuperada = buscarPorIdAtividade(idAtividade);
        verificarDatas(atividadeAtualizar, atividadeRecuperada);
        atividadeRecuperada.setTitulo(atividadeAtualizar.getTitulo());
        atividadeRecuperada.setDescricao(atividadeAtualizar.getDescricao());
        atividadeRecuperada.setDataEntrega(atividadeAtualizar.getDataEntrega());
        atividadeRecuperada.setPrograma(programaService.findByIdAtivo(atividadeAtualizar.getIdPrograma()));
        atividadeRecuperada.setPesoAtividade(atividadeAtualizar.getPesoAtividade());
        atividadeRecuperada.setNomeInstrutor(atividadeAtualizar.getNomeInstrutor());
        Set<ModuloEntity> moduloEntities = new HashSet<>();
        Set<AlunoEntity> alunoEntities = new HashSet<>();

        if (atividadeAtualizar.getModulos().size() > 0) {
            for (Integer modulos : atividadeAtualizar.getModulos()) {
                moduloEntities.add(moduloService.buscarPorIdModulo(modulos));
                atividadeRecuperada.setModulos(new HashSet<>(moduloEntities));
            }
        }

        if (atividadeAtualizar.getAlunos().size() > 0) {
            for (Integer alunos : atividadeAtualizar.getAlunos()) {
                alunoEntities.add(alunoService.findById(alunos));
                atividadeRecuperada.setAlunos(new HashSet<>(alunoEntities));
            }
        }
        atividadeRepository.save(atividadeRecuperada);
        return converterAtividadeDTO(atividadeRecuperada);
    }

    public AtividadeDTO findById(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        return converterAtividadeDTO(atividadeEntity);

    }

    public void desativar(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        atividadeEntity.setAtivo(Ativo.N);
        atividadeRepository.save(atividadeEntity);
    }

    public AtividadeEntity buscarPorIdAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return atividadeRepository.findByIdAtividadeAndAtivo(idAtividade, Ativo.S)
                .orElseThrow(() -> new RegraDeNegocioException("Atividade n??o encontrada. Insira um Id Valido!"));
    }

    public AtividadeDTO save(AtividadeEntity atividadeEntity) {
        return objectMapper.convertValue(atividadeRepository.save(atividadeEntity), AtividadeDTO.class);
    }

    private AtividadeDTO converterAtividadeDTO(AtividadeEntity atividade) {
        AtividadeDTO atividadeDTO = objectMapper.convertValue(atividade, AtividadeDTO.class);
        atividadeDTO.setPrograma(objectMapper.convertValue(atividade.getPrograma(), ProgramaDTO.class));
        List<ModuloAtividadeDTO> listModulo = atividade.getModulos()
                .stream()
                .map(moduloEntity -> objectMapper.convertValue(moduloEntity, ModuloAtividadeDTO.class)).toList();
        atividadeDTO.setModulos(listModulo);

        List<AtividadeAlunoDTO> listAlunos = atividade.getAlunos()
                .stream()
                .map(alunoEntity -> objectMapper.convertValue(alunoEntity, AtividadeAlunoDTO.class)).toList();
        atividadeDTO.setAlunos(listAlunos);

        return atividadeDTO;
    }

    private static void verificarDatas(AtividadeCreateDTO atividadeCreateDTO, AtividadeEntity atividadeEntity) throws RegraDeNegocioException {
        if (atividadeEntity.getDataCriacao().isAfter(atividadeCreateDTO.getDataEntrega())) {
            throw new RegraDeNegocioException("Data de abertura n??o pode ser maior que a data de fechamento no cadastro.");
        }
    }



//    public AtividadeDTO colocarAtividadeComoConcluida(Integer idAtividade) throws RegraDeNegocioException {
//        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
//        atividadeEntity.setStatusAtividade(AtividadeStatus.CONCLUIDA);
//        atividadeRepository.save(atividadeEntity);
//
//        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);
//
//    }

//    public PageDTO<AtividadeTrilhaDTO> listarAtividadePorStatus(Integer pagina, Integer tamanho, Integer idTrilha, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
//        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
//        Page<AtividadeTrilhaDTO> atividadeTrilha = atividadeRepository.listarAtividadePorStatus(pageRequest, idTrilha, atividadeStatus);
//
//        List<AtividadeTrilhaDTO> atividadeTrilhaDTOS = atividadeTrilha.getContent()
//                .stream()
//                .map(atividadeTrilhaDTO -> {
//                    objectMapper.convertValue(atividadeTrilhaDTO, AtividadeTrilhaDTO.class);
//                    return atividadeTrilhaDTO;
//                })
//                .toList();
//        if (atividadeTrilhaDTOS.isEmpty()) {
//            throw new RegraDeNegocioException("Atividade n??o cadastrada");
//        }
//
//        return new PageDTO<>(atividadeTrilha.getTotalElements(),
//                atividadeTrilha.getTotalPages(),
//                pagina,
//                tamanho,
//                atividadeTrilhaDTOS);
//    }

//    public PageDTO<AtividadeMuralDTO> listarAtividadeMuralInstrutor(Integer pagina, Integer tamanho, Integer idTrilha) throws RegraDeNegocioException {
//        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
//        Page<AtividadeMuralDTO> atividadeEntity = atividadeRepository.listarAtividadeMuralInstrutor(pageRequest, idTrilha);
//
//        List<AtividadeMuralDTO> atividadeMuralDTOList = atividadeEntity.getContent()
//                .stream()
//                .map(atividade -> objectMapper.convertValue(atividade, AtividadeMuralDTO.class))
//                .toList();
//
//        if (atividadeMuralDTOList.isEmpty()) {
//            throw new RegraDeNegocioException("Sem atividades no mural do instrutor");
//        }
//
//        return new PageDTO<>(atividadeEntity.getTotalElements(),
//                atividadeEntity.getTotalPages(),
//                pagina,
//                tamanho,
//                atividadeMuralDTOList);
//    }

//    public PageDTO<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer pagina, Integer tamanho, Integer idAluno, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
//        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
//        Page<AtividadeMuralAlunoDTO> atividadeEntity = atividadeRepository.listarAtividadeMuralAluno(idAluno, atividadeStatus, pageRequest);
//
//        List<AtividadeMuralAlunoDTO> atividadeMuralDTOList = atividadeEntity.getContent()
//                .stream()
//                .map(atividade -> objectMapper.convertValue(atividade, AtividadeMuralAlunoDTO.class))
//                .toList();
//
//        return new PageDTO<>(atividadeEntity.getTotalElements(),
//                atividadeEntity.getTotalPages(),
//                pagina,
//                tamanho,
//                atividadeMuralDTOList);
//    }

//    public PageDTO<AtividadeNotaPageDTO> listarAtividadePorIdTrilhaIdModulo(Integer pagina, Integer tamanho, Integer idTrilha, Integer idModulo, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
//        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
//        Page<AtividadeNotaPageDTO> atividadeEntity = atividadeRepository.listarAtividadePorIdTrilhaIdModulo(pageRequest, idTrilha, idModulo, atividadeStatus);
//
//        List<AtividadeNotaPageDTO> atividadeNotaDTOList = atividadeEntity.getContent()
//                .stream()
//                .map(atividade -> {
//                    AtividadeNotaPageDTO atividadeNotaDTO1 = objectMapper.convertValue(atividade, AtividadeNotaPageDTO.class);
//                    return atividadeNotaDTO1;
//                })
//                .toList();
//
//        return new PageDTO<>(atividadeEntity.getTotalElements(),
//                atividadeEntity.getTotalPages(),
//                pagina,
//                tamanho,
//                atividadeNotaDTOList);
//    }
}
