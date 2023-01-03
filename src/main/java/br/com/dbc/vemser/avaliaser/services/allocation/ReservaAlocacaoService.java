package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.ReservaAlocacaoEntity;
import br.com.dbc.vemser.avaliaser.entities.VagaEntity;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.allocation.ReservaAlocacaoRepository;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
import br.com.dbc.vemser.avaliaser.services.avaliaser.AlunoService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaAlocacaoService {
    private final ReservaAlocacaoRepository reservaAlocacaoRepository;
    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;
    private final VagaService vagaService;


    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        verificarDatas(reservaAlocacaoCreateDTO);
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);

        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        alunoService.verificarDisponibilidadeAluno(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO);
        alunoService.alterarStatusAluno(reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO);
        aluno.setSituacao(Situacao.RESERVADO);
        alunoRepository.save(aluno);

        try {
            adicionarQtdAlocadosEmVagas(reservaAlocacaoCreateDTO, reservaAlocacaoEntity, aluno);
            ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
//            aluno.getReservaAlocacaos().add(saveAlocacaoReserva);
            reservaAlocacaoEntity = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
            verificarAlunoReservado(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO);
        } catch (DataIntegrityViolationException ex) {
            throw new RegraDeNegocioException("Erro ao resevar, aluno já cadastrado!");
        }

        return converterEmDTO(reservaAlocacaoEntity);
    }

    public ReservaAlocacaoDTO editar(Integer idReserva, ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        verificarDatas(reservaAlocacaoCreateDTO);
        this.findById(idReserva);

        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        reservaAlocacaoEntity.setIdReservaAlocacao(idReserva);

        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
//        aluno.getReservaAlocacaos().add(saveAlocacaoReserva);

        adicionarQtdAlocadosEmVagas(reservaAlocacaoCreateDTO, reservaAlocacaoEntity, aluno);
        if (reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.RESERVADO)) {
            if (reservaAlocacaoCreateDTO.getIdVaga().equals(reservaAlocacaoEntity.getVaga().getIdVaga())) {
                alunoService.alterarStatusAluno(aluno.getIdAluno(), reservaAlocacaoCreateDTO);
                vagaService.alterarQuantidadeDeVagas(reservaAlocacaoEntity.getVaga().getIdVaga());
            }
        }

        return converterEmDTO(saveAlocacaoReserva);
    }

    public void verificarAlunoReservado(AlunoEntity alunoEntity,
                                        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        if (!alunoEntity.getSituacao().equals(Situacao.RESERVADO)) {
            vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());
        }
    }

    public PageDTO<ReservaAlocacaoDTO> filtrar(Integer page, Integer size, String nomeAluno, String nomeVaga) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
        PageRequest pageRequest = PageRequest.of(page, size);

        nomeAluno = nomeAluno != null ? "%" + nomeAluno + "%" : null;
        nomeVaga = nomeVaga != null ? "%" + nomeVaga + "%" : null;


        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository
                .findAllByFiltro(pageRequest, nomeAluno, nomeVaga);

        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = getReservaAlocacaoDTOS(reservaAlocacaoEntityPage);

        return new PageDTO<>(reservaAlocacaoEntityPage.getTotalElements(),
                reservaAlocacaoEntityPage.getTotalPages(),
                page,
                size,
                reservaAlocacaoDTOList);
        }
        List<ReservaAlocacaoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    @NotNull
    private List<ReservaAlocacaoDTO> getReservaAlocacaoDTOS(Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage) {
        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = reservaAlocacaoEntityPage
                .getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        return reservaAlocacaoDTOList;
    }

    public void deletar(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacao = findById(id);
        AlunoEntity aluno = reservaAlocacao.getAluno();
        reservaAlocacao.setSituacao(Situacao.INATIVO);
        aluno.setSituacao(Situacao.DISPONIVEL);
        reservaAlocacaoRepository.save(reservaAlocacao);
        vagaService.removerQuantidadeDeAlocados(reservaAlocacao.getVaga().getIdVaga());
        alunoRepository.save(aluno);

    }

    public PageDTO<ReservaAlocacaoDTO> listar(Integer page, Integer size)  throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository
                .findAll(pageRequest);

        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = getReservaAlocacaoDTOS(reservaAlocacaoEntityPage);

        return new PageDTO<>(reservaAlocacaoEntityPage.getTotalElements(),
                reservaAlocacaoEntityPage.getTotalPages(),
                page,
                size,
                reservaAlocacaoDTOList);
        }
        List<ReservaAlocacaoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = alunoService.findById(reservaAlocacaoCreateDTO.getIdAluno());
        VagaEntity vagaEntity = vagaService.findById(reservaAlocacaoCreateDTO.getIdVaga());
        vagaService.verificarVagaFechada(vagaEntity);


        if (reservaAlocacaoCreateDTO.getDataCancelamento() != null || reservaAlocacaoCreateDTO.getDataFinalizado() != null) {
            alunoService.alterarStatusAlunoCancelado(alunoEntity.getIdAluno(), reservaAlocacaoCreateDTO);
        }

        return new ReservaAlocacaoEntity(null,
                reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO.getDescricao(),
                reservaAlocacaoCreateDTO.getDataReserva(),
                reservaAlocacaoCreateDTO.getDataAlocacao(),
                reservaAlocacaoCreateDTO.getDataCancelamento(),
                null,
                reservaAlocacaoCreateDTO.getSituacao(),
                alunoEntity,
                vagaEntity);
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        VagaDTO vagaDTO = vagaService.converterEmDTO(reservaAlocacaoEntity.getVaga());
        AlunoDTO alunoDTO = alunoService.converterAlunoDTO(reservaAlocacaoEntity.getAluno());

        return new ReservaAlocacaoDTO(reservaAlocacaoEntity.getIdReservaAlocacao(),
                vagaDTO,
                alunoDTO,
                reservaAlocacaoEntity.getSituacao(),
                reservaAlocacaoEntity.getMotivo(),
                reservaAlocacaoEntity.getDataReserva(),
                reservaAlocacaoEntity.getDataAlocacao(),
                reservaAlocacaoEntity.getDataCancelamento(),
                reservaAlocacaoEntity.getDataFinalizado());
    }

    public void adicionarQtdAlocadosEmVagas(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO,
                                            ReservaAlocacaoEntity reservaAlocacaoEntity,
                                            AlunoEntity aluno) throws RegraDeNegocioException {
        if (reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.ALOCADO)) {
            if (!aluno.getSituacao().equals(Situacao.ALOCADO)) {
                alunoService.alterarStatusAluno(aluno.getIdAluno(), reservaAlocacaoCreateDTO);
                vagaService.adicionarQuantidadeDeAlocados(reservaAlocacaoEntity.getVaga().getIdVaga());
            }
        } else {
            if (aluno.getSituacao().equals(Situacao.ALOCADO)) {
                vagaService.removerQuantidadeDeAlocados(reservaAlocacaoCreateDTO.getIdVaga());

            }
        }

    }

    private static void verificarDatas(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        if(reservaAlocacaoCreateDTO.getDataReserva().isAfter(reservaAlocacaoCreateDTO.getDataAlocacao())) {
            throw new RegraDeNegocioException("Data de reserva não pode ser maior que a data de alocação.");
        }
        if(reservaAlocacaoCreateDTO.getDataAlocacao().isAfter(reservaAlocacaoCreateDTO.getDataCancelamento())) {
            throw new RegraDeNegocioException("Data de alocação não pode ser maior que a data de cancelamento.");
        }
        if(reservaAlocacaoCreateDTO.getDataCancelamento().isAfter(reservaAlocacaoCreateDTO.getDataFinalizado())) {
            throw new RegraDeNegocioException("Data de cancelamento não pode ser maior que a data de finalização.");
        }
    }
}
