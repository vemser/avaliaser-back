package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.vemrankser.trilhadto.TrilhaDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.entities.TecnologiaEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
import br.com.dbc.vemser.avaliaser.services.allocation.ProgramaService;
import br.com.dbc.vemser.avaliaser.services.allocation.TecnologiaService;
import br.com.dbc.vemser.avaliaser.services.vemrankser.TrilhaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ProgramaService programaService;
    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;
    private final TecnologiaService tecnologiaService;

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
            alunoEntity.setPrograma(programaService.findById(alunoCreateDTO.getIdPrograma()));
            alunoEntity.setTrilha(trilhaService.findById(alunoCreateDTO.getIdTrilha()));
            if(alunoCreateDTO.getTecnologias().size() > 0){
                for(Integer tecnologia: alunoCreateDTO.getTecnologias()){
                    alunoEntity.getTecnologia().add(tecnologiaService.findByIdTecnologia(tecnologia));
                }
            }
            alunoEntity.setAtivo(Ativo.S);

            AlunoEntity alunoSalvo = alunoRepository.save(alunoEntity);

            return converterAlunoDTO(alunoSalvo);
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
            aluno.setPrograma(programaService.findById(alunoAtualizado.getIdPrograma()));
            aluno.setTrilha(trilhaService.findById(alunoAtualizado.getIdTrilha()));
            if(alunoAtualizado.getTecnologias().size() > 0){
                aluno.getTecnologia().clear();
                for(Integer tecnologia: alunoAtualizado.getTecnologias()){
                    aluno.getTecnologia().add(tecnologiaService.findByIdTecnologia(tecnologia));
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

    private AlunoDTO converterAlunoDTO(AlunoEntity aluno) {

        AlunoDTO alunoDTO = objectMapper.convertValue(aluno, AlunoDTO.class);
        alunoDTO.setPrograma(objectMapper.convertValue(aluno.getPrograma(), ProgramaDTO.class));
        alunoDTO.setTrilha(objectMapper.convertValue(aluno.getTrilha(), TrilhaDTO.class));
        List<TecnologiaDTO> listaTecnologia = aluno.getTecnologia()
                .stream()
                        .map(tecnologia -> objectMapper.convertValue(tecnologia, TecnologiaDTO.class)).toList();
        alunoDTO.setTecnologias(listaTecnologia);

        return alunoDTO;
    }

    public void excluirAlunosTeste(Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        alunoRepository.delete(aluno);
    }

    public AlunoEntity alterarStatusAluno(Integer idAluno,
                                          ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = findById(idAluno);
        alunoEntity.setSituacao(reservaAlocacaoCreateDTO.getSituacao());
        return alunoRepository.save(alunoEntity);
    }

    public void alterarStatusAlunoCancelado(Integer idAluno,
                                            ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = findById(idAluno);
        if (reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.ALOCADO)
                || reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.RESERVADO)
                || reservaAlocacaoCreateDTO.getSituacao().equals(Ativo.N)) {
            alunoEntity.setSituacao(Situacao.DISPONIVEL);
        }
        alunoRepository.save(alunoEntity);
    }

    public void verificarDisponibilidadeAluno(AlunoEntity alunoEntity,
                                              ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {

        if (alunoEntity.getSituacao().equals(Situacao.ALOCADO)) {
            if (!reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.DISPONIVEL)) {
                throw new RegraDeNegocioException("Aluno não está disponivel!");
            }
        } else if (alunoEntity.getSituacao().equals(Situacao.RESERVADO)) {
            if (reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.RESERVADO)) {
                throw new RegraDeNegocioException("Aluno não está disponivel!");
            }
        }
    }


}
