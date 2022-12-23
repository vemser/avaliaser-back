package br.com.dbc.vemser.avaliaser.dto.vemrankser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeTrilhaDTO {

    private Integer idAtividade;
    private String nomeInstrutor;
    private String titulo;
    private Integer pesoAtividade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEntrega;
    private String nome;
    private Integer edicao;
    private LocalDate anoEdicao;
}
