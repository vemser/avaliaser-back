package br.com.dbc.vemser.avaliaser.dto.usuario;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TrocarSenhaUsuarioLogadoDTO {
    @NotNull
    @NotBlank
    private String senhaAntiga;
    @NotNull
    @NotBlank
    private String senhaNova;
}
