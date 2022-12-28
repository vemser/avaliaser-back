//package br.com.dbc.vemser.avaliaser.controllers.allocation;
//
//
//import br.com.dbc.vemser.avaliaser.controllers.adocumentation.TecnologiaInterfaceController;
//import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
//import br.com.dbc.vemser.avaliaser.services.allocation.TecnologiaService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/tecnologia")
//@Validated
//@RequiredArgsConstructor
//@Slf4j
//public class TecnologiaController implements TecnologiaInterfaceController {
//    private final TecnologiaService tecnologiaService;
//
//    @GetMapping("/tecnologia-busca")
//    public PageDTO<TecnologiaDTO> buscar(@RequestParam String nomeTecnologia,
//                                         @RequestParam int page,
//                                         @RequestParam int size) {
//        return tecnologiaService.buscarPorTecnologia(nomeTecnologia, PageRequest.of(page, size));
//    }
//}
