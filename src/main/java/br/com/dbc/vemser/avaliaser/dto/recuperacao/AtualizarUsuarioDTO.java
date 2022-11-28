package br.com.dbc.vemser.avaliaser.dto.recuperacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioDTO {
    private String nome;
    private String email;
}
