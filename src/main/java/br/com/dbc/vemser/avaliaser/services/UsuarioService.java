package br.com.dbc.vemser.avaliaser.services;


import br.com.dbc.vemser.avaliaser.dto.login.LoginDTO;
import br.com.dbc.vemser.avaliaser.dto.login.UsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.paginacaodto.PageDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.AtualizarUsuarioDTO;
import br.com.dbc.vemser.avaliaser.dto.recuperacao.UsuarioRecuperacaoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.AtualizarUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.TrocarSenhaUsuarioLogadoDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Cargo;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.UsuarioRepository;
import br.com.dbc.vemser.avaliaser.security.TokenService;
import br.com.dbc.vemser.avaliaser.services.enums.TipoEmails;
import br.com.dbc.vemser.avaliaser.utils.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    public PageDTO<UsuarioDTO> listUsuarioPaginado(Integer pagina, Integer tamanho) {
        if(tamanho != 0){
            PageRequest pageRequest = PageRequest.of(pagina, tamanho);
            Page<UsuarioEntity> paginaDoRepositorio = usuarioRepository.findAllByAtivo(Ativo.S, pageRequest);
            List<UsuarioDTO> usuarioPaginas = paginaDoRepositorio.getContent().stream()
                    .map(usuarioEntity -> converterUsuarioDTO(usuarioEntity))
                    .toList();
            return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                    paginaDoRepositorio.getTotalPages(),
                    pagina,
                    tamanho,
                    usuarioPaginas
            );
        }
        List<UsuarioDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L,0,0,tamanho,  listaVazia);
    }

    public UsuarioDTO cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO, Cargo cargo) throws RegraDeNegocioException {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}\\?";
        String senha = RandomStringUtils.random(8, characters);

        CargoEntity cargoBanco = cargoService.findById(cargo.getInteger());
        String senhaEncode = passwordEncoder.encode(senha);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(usuarioCreateDTO.getNome());
        usuarioEntity.setEmail(usuarioCreateDTO.getEmail());
        usuarioEntity.setSenha(senhaEncode);
        usuarioEntity.setCargo(cargoBanco);
        usuarioEntity.setAtivo(Ativo.S);

        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = new UsuarioRecuperacaoDTO(usuarioEntity.getEmail(), usuarioEntity.getNome(), senha);
        try {
            emailService.sendEmail(usuarioRecuperacaoDTO, TipoEmails.CREATE);
        } catch (Exception e){
            e.getMessage();
            throw new RegraDeNegocioException("Email inválido!");
        }

        usuarioRepository.save(usuarioEntity);

        UsuarioDTO usuarioDTO = converterUsuarioDTO(usuarioEntity);
        usuarioDTO.setCargo(usuarioEntity.getCargo().getNome());
        return usuarioDTO;
    }

    public UsuarioDTO atualizarUsuarioLogado(AtualizarUsuarioLogadoDTO atualizarUsuarioLogadoDTO) throws RegraDeNegocioException {

        UsuarioEntity usuarioLogado = findById(getLoggedUser().getIdUsuario());
        usuarioLogado.setNome(atualizarUsuarioLogadoDTO.getNome());

        return converterUsuarioDTO(usuarioRepository.save(usuarioLogado));
    }

    public UsuarioDTO atualizarUsuarioPorId(AtualizarUsuarioDTO atualizarUsuarioDTO, Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        usuarioEntity.setNome(atualizarUsuarioDTO.getNome());
        usuarioEntity.setEmail(atualizarUsuarioDTO.getEmail());
        UsuarioDTO usuarioLogadoDTO =
                converterUsuarioDTO(usuarioRepository.save(usuarioEntity));
        return usuarioLogadoDTO;

    }

    public void alterarSenhaUsuarioLogado(TrocarSenhaUsuarioLogadoDTO senhas) throws RegraDeNegocioException {
        UsuarioEntity usuario = getLoggedUser();
        if (passwordEncoder.matches(senhas.getSenhaAntiga(), usuario.getSenha())) {
            alterarSenha(senhas.getSenhaNova(), usuario.getIdUsuario());
        } else {
            throw new RegraDeNegocioException("Senha atual informada está incorreta! " +
                    "Não é possível alterar senha.");
        }
    }

    public void alterarSenhaPorRecuperacao(String senha) throws RegraDeNegocioException {
        Integer idUsuario = getUsuarioLogado().getIdUsuario();
        alterarSenha(senha, idUsuario);
    }


    public void recuperarSenha(String emailDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findByEmail(emailDTO);
        String token = tokenService.retornarTokenRecuperacaoSenha(usuarioEntity);
        UsuarioRecuperacaoDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioRecuperacaoDTO.class);
        emailService.sendEmailRecuperacao(usuarioDTO, TipoEmails.REC_SENHA, token);
    }

    public UsuarioEntity alterarSenha(String senha, Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = this.findById(idUsuario);
        String senhaNova = passwordEncoder.encode(senha);
        usuarioEntity.setSenha(senhaNova);
        return usuarioRepository.save(usuarioEntity);
    }

    public UsuarioEntity findByEmail(String email) throws RegraDeNegocioException {
        return usuarioRepository.findByEmailAndAtivo(email, Ativo.S)
                .orElseThrow(() -> new RegraDeNegocioException("USUARIO_NAO_ENCONTRADO"));
    }

//    public UsuarioDTO alterarImagemUsuarioLogado(MultipartFile file) throws RegraDeNegocioException {
//        UsuarioLogadoDTO usuario = getUsuarioLogado();
//        return uploadImagem(file, usuario.getIdUsuario());
//    }

    public UsuarioDTO uploadImagem(MultipartFile imagem, Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).get();
        usuarioEntity.setImage(transformarImagemEmBytes(imagem));
        UsuarioDTO usuarioLogadoDTO =
                converterUsuarioDTO(usuarioRepository.save(usuarioEntity));
        return usuarioLogadoDTO;
    }

    public String loginUsuario(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(userPassAuthToken);

        Object principal = authenticate.getPrincipal();
        UsuarioEntity usuario = (UsuarioEntity) principal;
        return tokenService.getToken(usuario);
    }

    public UsuarioLogadoDTO getUsuarioLogado() throws RegraDeNegocioException {

        UsuarioEntity usuario = getLoggedUser();
        UsuarioLogadoDTO usuarioLogado = new UsuarioLogadoDTO();
        usuarioLogado.setIdUsuario(usuario.getIdUsuario());
        usuarioLogado.setNome(usuario.getNome());
        usuarioLogado.setEmail(usuario.getEmail());
        if (usuario.getImage() != null) {
            usuarioLogado.setFoto(ImageUtil.decompressImage(usuario.getImage()));
        }
        usuarioLogado.setCargo(Cargo.valueOf(usuario.getCargo().getNome().replace("ROLE_", "")).getDescricao());
        return usuarioLogado;
    }

    public void desativarUsuarioById(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(id);
        usuario.setAtivo(Ativo.N);
        usuarioRepository.save(usuario);
    }

    public void excluirUsuariosTeste(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

    public UsuarioEntity getLoggedUser() throws RegraDeNegocioException {
        return findById(getIdLoggedUser());
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findByAtivoAndIdUsuario(Ativo.S,id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    }

    public UsuarioDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(id);
        return converterUsuarioDTO(usuario);
    }

    private Integer getIdLoggedUser() {
        return Integer.parseInt(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }
    


    private UsuarioDTO converterUsuarioDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuarioEntity.getIdUsuario());
        usuarioDTO.setNome(usuarioEntity.getNome());
        usuarioDTO.setEmail(usuarioEntity.getEmail());
        usuarioDTO.setCargo(Cargo.valueOf(usuarioEntity.getCargo().getNome().replace("ROLE_", "")).getDescricao());
        if(usuarioEntity.getImage() != null){
            usuarioDTO.setFoto(ImageUtil.decompressImage(usuarioEntity.getImage()));
        }
        return usuarioDTO;
    }

    public static byte[] transformarImagemEmBytes(MultipartFile imagem) throws RegraDeNegocioException {
        try {
            byte[] imagemBytes = imagem.getBytes();
            byte[] imagemRecebida = ImageUtil.compressImage(imagemBytes);
            return imagemRecebida;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao converter imagem.");
        }
    }
}
