package br.com.dbc.vemser.avaliaser.dto.avalaliaser.acompanhamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditarAcompanhamentoDTO {
    @NotNull(message = "Titulo não pode ser nulo.")
    @NotBlank(message = "Titulo não pode ficar em branco.")
    private String titulo;

    @NotNull(message = "Descrição não pode ser nulo.")
    @NotBlank(message = "Descrição não pode ficar em branco.")
    private String descricao;
}
