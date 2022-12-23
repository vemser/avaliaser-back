package br.com.dbc.vemser.avaliaser.dto.avalaliaser.usuario;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TrocarSenhaUsuarioLogadoDTO {
    @NotNull(message = "Senha antiga não pode ser nula")
    @NotBlank(message = "Senha antiga não pode ficar em branco.")
    @Size(min = 8, max = 16, message = "Senha precisa ter entre 8 e 16 caracteres.")
    private String senhaAntiga;

    @NotNull(message = "Senha nova não pode ser nula")
    @NotBlank(message = "Senha nova não pode ficar em branco.")
    @Size(min = 8, max = 16, message = "Senha precisa ter entre 8 e 16 caracteres.")
    private String senhaNova;
}
