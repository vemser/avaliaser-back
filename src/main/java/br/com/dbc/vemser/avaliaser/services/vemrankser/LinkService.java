//package br.com.dbc.vemser.avaliaser.services.vemrankser;
//
//import br.com.vemrankser.ranqueamento.dto.LinkDTO;
//import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
//import br.com.vemrankser.ranqueamento.entity.LinkEntity;
//import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.repository.LinkRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class LinkService {
//
//    private final LinkRepository linkRepository;
//    private final ObjectMapper objectMapper;
//    private final UsuarioService usuarioService;
//    private final AtividadeService atividadeService;
//
//    public LinkDTO create(Integer idAtividade, Integer idAluno, LinkDTO linkDTO) throws RegraDeNegocioException {
//        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);
//        UsuarioEntity usuarioEntity = usuarioService.findById(idAluno);
//        LinkEntity linkEntity = objectMapper.convertValue(linkDTO, LinkEntity.class);
//        linkEntity.setAtividade(atividadeEntity);
//        linkEntity.setUsuario(usuarioEntity);
//        linkEntity.setIdAtividade(atividadeEntity.getIdAtividade());
//        linkEntity.setIdUsuario(usuarioEntity.getIdUsuario());
//
//        return objectMapper.convertValue(linkRepository.save(linkEntity), LinkDTO.class);
//
//
//    }
//
//
//}
