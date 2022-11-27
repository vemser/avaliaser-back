package br.com.dbc.vemser.avaliaser.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioLogadoDTO {
    private Integer idUsuario;
    private String nome;
    private byte[] foto;
    private String cargo;
}
