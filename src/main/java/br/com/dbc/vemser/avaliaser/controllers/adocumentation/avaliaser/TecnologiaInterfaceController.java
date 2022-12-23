package br.com.dbc.vemser.avaliaser.controllers.adocumentation.avaliaser;


import br.com.dbc.vemser.avaliaser.dto.allocation.tecnologia.TecnologiaDTO;
import br.com.dbc.vemser.avaliaser.dto.avalaliaser.paginacaodto.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface TecnologiaInterfaceController {
    @Operation(summary = "Listar pagina de tecnologias", description = "Lista uma pagina de tecnologias")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Tecnologia Listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    PageDTO<TecnologiaDTO> buscar(@RequestParam String nomeTecnologia,
                                  @RequestParam int page,
                                  @RequestParam int size);
}
