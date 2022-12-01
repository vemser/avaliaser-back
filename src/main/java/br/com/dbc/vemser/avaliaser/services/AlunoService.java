package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.AlunoRepository;
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
    private final CargoService cargoService;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    public PageDTO<AlunoDTO> listarAlunoPaginado(Integer pagina, Integer tamanho) {
        if (tamanho != 0) {
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<AlunoEntity> paginaDoRepositorio = alunoRepository.findAllByAtivo(Ativo.S, pageRequest);
            List<AlunoDTO> alunoPaginas = paginaDoRepositorio.getContent().stream().map(alunoEntity -> objectMapper.convertValue(alunoEntity, AlunoDTO.class)).toList();

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
            AlunoDTO alunoDTO = objectMapper.convertValue(alunoSalvo, AlunoDTO.class);
            return alunoDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Email já consta como cadastrado no nosso sistema!");
        }
    }

    public AlunoDTO uploadImagem(MultipartFile imagem, Integer id) throws RegraDeNegocioException {
        AlunoEntity aluno = findById(id);
        aluno.setFoto(transformarImagemEmBytes(imagem));
        AlunoDTO alunoDTO = objectMapper.convertValue(alunoRepository.save(aluno), AlunoDTO.class);
        return alunoDTO;
    }

    public AlunoDTO atualizarAlunoPorId(Integer id, AlunoCreateDTO alunoAtualizado, Stack stack) throws RegraDeNegocioException {
        try {
            AlunoEntity aluno = findById(id);
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setEmail(alunoAtualizado.getEmail());
            aluno.setStack(stack);
            AlunoDTO alunoDTO = objectMapper.convertValue(alunoRepository.save(aluno), AlunoDTO.class);
            return alunoDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Email já consta como cadastrado no nosso sistema!");
        }
    }

    public void desativarAlunoById(Integer id) throws RegraDeNegocioException {
        String cargoGestor = cargoService.findById(2).getNome();
        String cargoInstrutor = cargoService.findById(3).getNome();
        String cargo = usuarioService.getUsuarioLogado().getCargo();
        if (cargo.equals(cargoGestor) || cargo.equals(cargoInstrutor)) {
            AlunoEntity aluno = findById(id);
            aluno.setAtivo(Ativo.N);
            alunoRepository.save(aluno);
        } else {
            throw new RegraDeNegocioException("Você não tem permissão para excluir de Alunos!");
        }
    }

    private AlunoDTO converterAlunoDTO(AlunoEntity aluno) {
        AlunoDTO alunoDTO = objectMapper.convertValue(aluno, AlunoDTO.class);
        return alunoDTO;
    }

    private static byte[] transformarImagemEmBytes(MultipartFile imagem) throws RegraDeNegocioException {
        try {
            byte[] imagemBytes = imagem.getBytes();
            byte[] imagemRecebida = ImageUtil.compressImage(imagemBytes);
            return imagemRecebida;
        } catch (IOException e) {
            throw new RegraDeNegocioException("Erro ao converter imagem.");
        }
    }

}
