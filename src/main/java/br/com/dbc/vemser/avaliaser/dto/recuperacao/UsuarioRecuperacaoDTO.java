package br.com.dbc.vemser.avaliaser.dto.recuperacao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRecuperacaoDTO {
    private String email;
    private String nome;
    private String senha;
}
