package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.dto.allocation.reservaAlocacao.ReservaAlocacaoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.AlunoRepository;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;


    public PageDTO<AlunoDTO> listarAlunoPaginado(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (tamanho > 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<AlunoEntity> paginaDoRepositorio = alunoRepository.findAllByAtivo(Ativo.S, pageRequest);
            List<AlunoDTO> alunoPaginas = paginaDoRepositorio.getContent().stream().map(this::converterAlunoDTO).toList();

            return new PageDTO<>(paginaDoRepositorio.getTotalElements(), paginaDoRepositorio.getTotalPages(), pagina, tamanho, alunoPaginas);
        }
        List<AlunoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public AlunoEntity findById(Integer id) throws RegraDeNegocioException {
        return alunoRepository.findByAtivoAndIdAluno(Ativo.S, id).orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado."));
    }

    public AlunoDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        return converterAlunoDTO(aluno);
    }

    public AlunoDTO cadastrarAluno(AlunoCreateDTO alunoCreateDTO, Stack stack) throws RegraDeNegocioException {
        try {
            AlunoEntity alunoEntity = new AlunoEntity();
            alunoEntity.setNome(alunoCreateDTO.getNome());
            alunoEntity.setEmail(alunoCreateDTO.getEmail());
            alunoEntity.setStack(stack);
            alunoEntity.setAtivo(Ativo.S);

            AlunoEntity alunoSalvo = alunoRepository.save(alunoEntity);

            return converterAlunoDTO(alunoSalvo);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Email já consta como cadastrado no nosso sistema!");
        }
    }

    public AlunoDTO uploadImagem(MultipartFile imagem, Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        aluno.setFoto(transformarImagemEmBytes(imagem));
        AlunoEntity alunoEntity = alunoRepository.save(aluno);

        return converterAlunoDTO(alunoEntity);
    }

    public AlunoDTO atualizarAlunoPorId(Integer id, AlunoCreateDTO alunoAtualizado, Stack stack) throws RegraDeNegocioException {
        try {
            AlunoEntity aluno = findById(id);
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setStack(stack);
            if (!aluno.getEmail().equals(alunoAtualizado.getEmail())) {
                aluno.setEmail(alunoAtualizado.getEmail());
            }
            AlunoEntity alunoEntity = alunoRepository.save(aluno);

            return converterAlunoDTO(alunoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Email já consta como cadastrado no nosso sistema!");
        }
    }

    public void desativarAlunoById(Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        aluno.setAtivo(Ativo.N);
        alunoRepository.save(aluno);
    }

    private AlunoDTO converterAlunoDTO(AlunoEntity aluno) {
        AlunoDTO alunoDTO = objectMapper.convertValue(aluno, AlunoDTO.class);
        if (aluno.getFoto() != null) {
            alunoDTO.setFoto(ImageUtil.decompressImage(aluno.getFoto()));
        }
        return alunoDTO;
    }

    public byte[] transformarImagemEmBytes(MultipartFile imagem) throws RegraDeNegocioException {
        try {
            byte[] imagemBytes = imagem.getBytes();
            byte[] imagemRecebida = ImageUtil.compressImage(imagemBytes);
            return imagemRecebida;
        } catch (IOException e) {
            throw new RegraDeNegocioException("Erro ao converter imagem.");
        }
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
                || reservaAlocacaoCreateDTO.getSituacao().equals(Situacao.INATIVO))
        { alunoEntity.setSituacao(Situacao.DISPONIVEL);
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
