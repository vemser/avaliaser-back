package br.com.dbc.vemser.avaliaser.dto.login;

import br.com.dbc.vemser.avaliaser.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    private String email;
    private String senha;
    private String nome;
    private Cargo cargo;
}
