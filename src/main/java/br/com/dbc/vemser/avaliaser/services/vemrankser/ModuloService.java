//package br.com.dbc.vemser.avaliaser.services.vemrankser;
//
//import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
//import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
//import br.com.vemrankser.ranqueamento.dto.ModuloTrilhaDTO;
//import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
//import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
//import br.com.vemrankser.ranqueamento.enums.StatusModulo;
//import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
//import br.com.vemrankser.ranqueamento.repository.ModuloRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Slf4j
//@Service
//public class ModuloService {
//
//    private final ModuloRepository moduloRepository;
//
//    private final TrilhaService trilhaService;
//    private final ObjectMapper objectMapper;
//
//
//    public ModuloDTO adicionar(ModuloCreateDTO modulo) {
//        ModuloEntity moduloEntityNovo = objectMapper.convertValue(modulo, ModuloEntity.class);
//        moduloEntityNovo.setStatusModulo(StatusModulo.S);
//        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
//        moduloEntityNovo.setDataInicio(now);
//        moduloRepository.save(moduloEntityNovo);
//        return objectMapper.convertValue(moduloEntityNovo, ModuloDTO.class);
//
//    }
//
//    public ModuloEntity buscarPorIdModulo(Integer idModulo) throws RegraDeNegocioException {
//        return moduloRepository.findById(idModulo)
//                .orElseThrow(() -> new RegraDeNegocioException("Modulo não encontrado."));
//    }
//
//    public ModuloDTO findById(Integer idModulo) throws RegraDeNegocioException {
//        ModuloEntity moduloEntity = buscarPorIdModulo(idModulo);
//        return objectMapper.convertValue(moduloEntity, ModuloDTO.class);
//
//    }
//
//    List<ModuloDTO> listarModulo() {
//        return moduloRepository.findAll()
//                .stream()
//                .map(modulo -> objectMapper.convertValue(modulo, ModuloDTO.class))
//                .toList();
//    }
//
//    public ModuloDTO vincularModuloTrilha(Integer idModulo, Integer idTrilha, ModuloTrilhaDTO moduloTrilhaDTO) throws RegraDeNegocioException {
//        ModuloEntity moduloEntity = buscarPorIdModulo(idModulo);
//        TrilhaEntity trilhaEntity = trilhaService.findById(idTrilha);
//        moduloEntity.getTrilhas().add(trilhaEntity);
//        moduloRepository.save(moduloEntity);
//        return objectMapper.convertValue(moduloEntity, ModuloDTO.class);
//    }
//
//    public List<ModuloDTO> listAllModulos() {
//        return moduloRepository.findAll().stream()
//                .map(moduloEntity -> objectMapper.convertValue(moduloEntity, ModuloDTO.class))
//                .toList();
//    }
//}
