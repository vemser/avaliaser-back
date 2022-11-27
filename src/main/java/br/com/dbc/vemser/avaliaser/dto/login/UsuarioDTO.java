package br.com.dbc.vemser.avaliaser.dto.login;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
    private byte[] foto;
    private String cargo;
}
