package br.com.dbc.vemser.avaliaser.dto.allocation.vaga;

import br.com.dbc.vemser.avaliaser.dto.allocation.cliente.ClienteDTO;
import br.com.dbc.vemser.avaliaser.dto.allocation.programa.ProgramaDTO;
import br.com.dbc.vemser.avaliaser.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO {

    private Integer idVaga;
    private String nome;
    private Integer quantidade;
    private Integer quantidade_disponiveis;
    private Integer quantidadeAlocados;
    private Situacao situacao;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private LocalDate dataCriacao;
    private ClienteDTO cliente;
    private List<ProgramaDTO> programas;
}
