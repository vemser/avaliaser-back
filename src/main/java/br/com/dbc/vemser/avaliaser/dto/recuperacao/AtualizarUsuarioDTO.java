package br.com.dbc.vemser.avaliaser.dto.recuperacao;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;
}
