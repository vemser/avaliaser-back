package br.com.dbc.vemser.avaliaser.dto.vemrankser.atividadegeraldto.atividadepagedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadePaginacaoDTO<T> {

    private Long totalElementos;
    private Integer quantidadePaginas;
    private Integer pagina;
    private Integer tamanho;
    private List<T> elementos;
}
