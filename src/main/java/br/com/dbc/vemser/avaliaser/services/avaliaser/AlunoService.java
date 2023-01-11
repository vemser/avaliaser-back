package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import br.com.dbc.vemser.avaliaser.services.allocation.TecnologiaService;
import br.com.dbc.vemser.avaliaser.services.allocation.VagaService;
import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ProgramaService programaService;
    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;
    private final TecnologiaService tecnologiaService;
    private final VagaService vagaService;

    public PageDTO<AlunoDTO> listarAlunoPaginado(Integer idAluno, String nome, String email, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            Page<AlunoEntity> paginaDoRepositorio = filtrarAlunos(idAluno, nome, email, pagina, tamanho);
            List<AlunoDTO> alunoPaginas = paginaDoRepositorio.getContent().stream().map(this::converterAlunoDTO).toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(), paginaDoRepositorio.getTotalPages(), pagina, tamanho, alunoPaginas);
        }
        List<AlunoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public AlunoDTO findByEmail(String email) throws RegraDeNegocioException {
        if(!email.contains("@")){
            email = email + "@dbccompany.com.br";
        }
        AlunoEntity alunoEntity = alunoRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado!"));
        return converterAlunoDTO(alunoEntity);
    }

    public AlunoDTO cadastrarAluno(AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        try {
            AlunoEntity alunoEntity = new AlunoEntity();
            alunoEntity.setNome(alunoCreateDTO.getNome());
            alunoEntity.setTelefone(alunoCreateDTO.getTelefone());
            alunoEntity.setEmail(alunoCreateDTO.getEmail());
            alunoEntity.setCidade(alunoCreateDTO.getCidade());
            alunoEntity.setEstado(alunoCreateDTO.getEstado());
            alunoEntity.setPontuacao(0);
            alunoEntity.setDescricao(alunoCreateDTO.getDescricao());
            alunoEntity.setSituacao(alunoCreateDTO.getSituacao());
            alunoEntity.setPrograma(programaService.findByIdAtivo(alunoCreateDTO.getIdPrograma()));
            alunoEntity.setTrilha(trilhaService.findById(alunoCreateDTO.getIdTrilha()));
            if (alunoCreateDTO.getTecnologias().size() > 0) {
                for (Integer tecnologia : alunoCreateDTO.getTecnologias()) {
                    TecnologiaEntity tecnologiaEntity = tecnologiaService.findByIdTecnologia(tecnologia);
                    if (!(tecnologiaEntity == null)) {
                        alunoEntity.getTecnologia().add(tecnologiaEntity);
                    }
                }
            }
            alunoEntity.setAtivo(Ativo.S);

            AlunoEntity alunoSalvo = alunoRepository.save(alunoEntity);

            return converterAlunoDTO(alunoSalvo);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Email já consta como cadastrado no nosso sistema!");
        }
    }

    public AlunoDTO atualizarAlunoPorId(Integer id, AlunoCreateDTO alunoAtualizado) throws RegraDeNegocioException {
        try {
            AlunoEntity aluno = findById(id);
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setTelefone(alunoAtualizado.getTelefone());
            aluno.setCidade(alunoAtualizado.getCidade());
            aluno.setEstado(alunoAtualizado.getEstado());
            aluno.setDescricao(alunoAtualizado.getDescricao());
            aluno.setSituacao(alunoAtualizado.getSituacao());
            aluno.setPrograma(programaService.findByIdAtivo(alunoAtualizado.getIdPrograma()));
            aluno.setTrilha(trilhaService.findById(alunoAtualizado.getIdTrilha()));
            if (alunoAtualizado.getTecnologias().size() > 0) {
                aluno.getTecnologia().clear();
                for (Integer tecnologia : alunoAtualizado.getTecnologias()) {
                    TecnologiaEntity tecnologiaEntity = tecnologiaService.findByIdTecnologia(tecnologia);
                    if (!(tecnologiaEntity == null)) {
                        aluno.getTecnologia().add(tecnologiaEntity);
                    }
                }
            }
            if (!aluno.getEmail().equals(alunoAtualizado.getEmail())) {
                aluno.setEmail(alunoAtualizado.getEmail());
            }
            AlunoEntity alunoEntity = alunoRepository.save(aluno);

            return converterAlunoDTO(alunoEntity);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Email já consta como cadastrado no nosso sistema!");
        }
    }

    public void desativarAlunoById(Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        aluno.setAtivo(Ativo.N);
        alunoRepository.save(aluno);
    }

    public PageDTO<AlunoDTO> listarDisponiveis(Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AlunoEntity> paginaRepository = alunoRepository.findAllBySituacao(pageRequest, SituacaoReserva.DISPONIVEL);

            List<AlunoDTO> alunoDTOList = paginaRepository.getContent().stream()
                    .map(this::converterAlunoDTO)
                    .collect(Collectors.toList());

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    page,
                    size,
                    alunoDTOList);
        }
        List<AlunoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    private Page<AlunoEntity> filtrarAlunos(Integer idAluno, String nome, String email, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idAluno == null)) {
            return alunoRepository.findAllByIdAlunoAndAtivo(idAluno, Ativo.S, pageRequest);
        } else if (!(nome == null)) {
            return alunoRepository.findAllByNomeContainingIgnoreCaseAndAtivo(nome, Ativo.S, pageRequest);
        } else if (!(email == null)) {
            return alunoRepository.findAllByEmailContainingIgnoreCaseAndAtivo(email, Ativo.S, pageRequest);
        }
        return alunoRepository.findAllByAtivo(Ativo.S, pageRequest);
    }

    public AlunoEntity findById(Integer idAluno) throws RegraDeNegocioException {
        return alunoRepository.findById(idAluno).orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado."));
    }

    public AlunoDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        return converterAlunoDTO(aluno);
    }

    public AlunoDTO converterAlunoDTO(AlunoEntity aluno) {

        AlunoDTO alunoDTO = objectMapper.convertValue(aluno, AlunoDTO.class);
        alunoDTO.setPrograma(objectMapper.convertValue(aluno.getPrograma(), ProgramaDTO.class));
        alunoDTO.setTrilha(objectMapper.convertValue(aluno.getTrilha(), TrilhaDTO.class));
        List<TecnologiaDTO> listaTecnologia = aluno.getTecnologia()
                .stream()
                .map(tecnologia -> objectMapper.convertValue(tecnologia, TecnologiaDTO.class)).toList();
        alunoDTO.setTecnologias(listaTecnologia);

        return alunoDTO;
    }

    public AlunoEntity alterarStatusAluno(Integer idAluno,
                                          SituacaoReserva situacao) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = findById(idAluno);
        alunoEntity.setSituacao(situacao);
        return alunoRepository.save(alunoEntity);
    }


    public void verificarDisponibilidadeAluno(AlunoEntity alunoEntity,
                                              SituacaoReserva situacaoReserva) throws RegraDeNegocioException {

        if (alunoEntity.getSituacao().equals(SituacaoReserva.ALOCADO)) {
            if (!situacaoReserva.equals(SituacaoReserva.DISPONIVEL)) {
                throw new RegraDeNegocioException("Aluno não pode ser Reservado!");
            }
        } else if (alunoEntity.getSituacao().equals(SituacaoReserva.RESERVADO)) {
            if (situacaoReserva.equals(SituacaoReserva.RESERVADO)) {
                throw new RegraDeNegocioException("Aluno não pode ser Reservado!");
            }
        }
    }

    public void verificaSituacaoAluno(AlunoEntity aluno, Integer idVaga, SituacaoReserva situacao) throws RegraDeNegocioException {
        boolean alunoAlocadoouCancelado = (aluno.getSituacao().equals(SituacaoReserva.ALOCADO) || aluno.getSituacao().equals(SituacaoReserva.CANCELADO));
        if (alunoAlocadoouCancelado && situacao.equals(SituacaoReserva.RESERVADO)) {
            throw new RegraDeNegocioException("Aluno não pode ser Reservado!");
        }
        if (situacao.equals(SituacaoReserva.ALOCADO)) {
            alterarStatusAluno(aluno.getIdAluno(), situacao);
            vagaService.adicionarQuantidadeDeAlocados(idVaga);
        } else if(aluno.getSituacao().equals(SituacaoReserva.ALOCADO) && situacao.equals(SituacaoReserva.CANCELADO)){
            alterarStatusAluno(aluno.getIdAluno(), situacao);
            vagaService.removerQuantidadeDeAlocados(idVaga);
        }
    }

    public PageDTO<AlunoDTO> listarAlunosAtivoPorProgramaTrilha(Integer idPrograma,List<Integer> idTrilhas, Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AlunoEntity> paginaDoRepositorio = alunoRepository.findAllByTrilha_IdTrilhaInAndProgramaIdProgramaAndAtivo(idTrilhas, idPrograma, Ativo.S, pageRequest);
            List<AlunoDTO> aluno = paginaDoRepositorio.getContent().stream()
                    .map(this::converterAlunoDTO)
                    .toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    page,
                    size,
                    aluno);
        }
        List<AlunoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);

    }
}


