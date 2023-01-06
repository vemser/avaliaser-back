package br.com.dbc.vemser.avaliaser.services.allocation;


import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoEditarDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.vaga.VagaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.ReservaAlocacaoEntity;
import br.com.dbc.vemser.avaliaser.entities.VagaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
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
    private final VagaService vagaService;


    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        reservaAlocacaoEntity.setVaga(vagaService.findById(reservaAlocacaoCreateDTO.getIdVaga()));
        reservaAlocacaoEntity.setAluno(alunoService.findById(reservaAlocacaoCreateDTO.getIdAluno()));
        reservaAlocacaoEntity.setAtivo(Ativo.S);

        alunoService.verificarDisponibilidadeAluno(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO.getSituacao());
        alunoService.alterarStatusAluno(reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO.getSituacao());

        try {
            AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
            adicionarQtdAlocadosEmVagas(reservaAlocacaoEntity, aluno, reservaAlocacaoCreateDTO.getSituacao());
            reservaAlocacaoRepository.save(reservaAlocacaoEntity);
            reservaAlocacaoEntity = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
            verificarAlunoReservado(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO);

        } catch (DataIntegrityViolationException ex) {
            throw new RegraDeNegocioException("Erro ao reservar, aluno já cadastrado!");
        }

        return converterEmDTO(reservaAlocacaoEntity);
    }

    public ReservaAlocacaoDTO editar(Integer idReserva, ReservaAlocacaoEditarDTO reservaAlocacaoEditarDTO) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reserva = findById(idReserva);
        AlunoEntity aluno = reserva.getAluno();

        reserva.setSituacao(reservaAlocacaoEditarDTO.getSituacao());
        reserva.setDescricao(reservaAlocacaoEditarDTO.getDescricao());
        if(!aluno.getSituacao().equals(reservaAlocacaoEditarDTO.getSituacao())) {
            alunoService.verificaSituacaoAluno(aluno, reserva.getVaga().getIdVaga(), reservaAlocacaoEditarDTO.getSituacao());
        }else{
            throw new RegraDeNegocioException("Não pode ser alterada a situação para: " + reservaAlocacaoEditarDTO.getSituacao().getSituacao());
        }
        ReservaAlocacaoEntity reservaSalva = reservaAlocacaoRepository.save(reserva);
        return converterEmDTO(reservaSalva);
    }

    public void verificarAlunoReservado(AlunoEntity alunoEntity,
                                        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        if (!alunoEntity.getSituacao().equals(SituacaoReserva.RESERVADO)) {
            vagaService.adicionarQuantidadeDeAlocados(reservaAlocacaoCreateDTO.getIdVaga());
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

    private List<ReservaAlocacaoDTO> getReservaAlocacaoDTOS(Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage) {
        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = reservaAlocacaoEntityPage
                .getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        return reservaAlocacaoDTOList;
    }


    public PageDTO<ReservaAlocacaoDTO> listar(Integer page, Integer size)  throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository.findAll(pageRequest);

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

        ReservaAlocacaoEntity reserva = new ReservaAlocacaoEntity();
        reserva.setIdReservaAlocacao(null);
        reserva.setMotivo(null);
        reserva.setVaga(vagaEntity);
        reserva.setAluno(alunoEntity);
        reserva.setDescricao(reservaAlocacaoCreateDTO.getDescricao());
        reserva.setSituacao(reservaAlocacaoCreateDTO.getSituacao());
        return reserva;
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        VagaDTO vagaDTO = vagaService.converterEmDTO(reservaAlocacaoEntity.getVaga());
        AlunoDTO alunoDTO = alunoService.converterAlunoDTO(reservaAlocacaoEntity.getAluno());

        return new ReservaAlocacaoDTO(reservaAlocacaoEntity.getIdReservaAlocacao(),
                vagaDTO,
                alunoDTO,
                reservaAlocacaoEntity.getSituacao(),
                reservaAlocacaoEntity.getDescricao());
    }

    public void adicionarQtdAlocadosEmVagas(ReservaAlocacaoEntity reservaAlocacaoEntity,
                                            AlunoEntity aluno,
                                            SituacaoReserva situacao) throws RegraDeNegocioException {
        if (situacao.equals(SituacaoReserva.ALOCADO)) {
            if (!aluno.getSituacao().equals(SituacaoReserva.ALOCADO)) {
                alunoService.alterarStatusAluno(aluno.getIdAluno(), situacao);
                vagaService.adicionarQuantidadeDeAlocados(reservaAlocacaoEntity.getVaga().getIdVaga());
            }
        } else {
            if (aluno.getSituacao().equals(SituacaoReserva.ALOCADO)) {
                vagaService.removerQuantidadeDeAlocados(reservaAlocacaoEntity.getVaga().getIdVaga());

            }
        }

    }

}
