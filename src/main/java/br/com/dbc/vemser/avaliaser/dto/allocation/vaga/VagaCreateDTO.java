package br.com.dbc.vemser.avaliaser.dto.allocation.vaga;


import br.com.dbc.vemser.avaliaser.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VagaCreateDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome da vaga", example = "Desenvolvedor(a) Java - Back-End")
    private String nome;

    @NotNull(message = "Quantidade não pode ser vazio ou nulo.")
    @Schema(description = "Quantidade de pessoas para a vaga", example = "2")
    private Integer quantidade;

    @NotNull(message = "Id programa não pode ser nulo.")
    @Schema(description = "Id do programa", example = "1")
    private Integer idPrograma;

    @NotNull(message = "situacao não pode ser nulo.")
    @Schema(description = "situacao da vaga", example = "ATIVO")
    private Situacao situacao;

    @Schema(description = "Data abertura vaga", example = "2022-12-20")
    @FutureOrPresent
    private LocalDate dataAbertura;

    @Schema(description = "Data fechamento vaga", example = "2022-12-26")
    @FutureOrPresent
    private LocalDate dataFechamento;

    @Schema(description = "Data criação")
    @FutureOrPresent
    private LocalDate dataCriacao;

    @NotNull(message = "Id cliente não pode ser vazio ou nulo.")
    @Schema(description = "Id do cliente", example = "16")
    private Integer idCliente;

}
