package br.com.dbc.vemser.avaliaser.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioLogadoDTO {
    @Schema(example = "1")
    private Integer idUsuario;
    @Schema(example = "Noah Bispo")
    private String nome;
    @Schema(example = "noah.bispo@dbccompany.com.br")
    private String email;
    @Schema(example = " ", description = "Imagem de Perfil do Usuario")
    private byte[] foto;
    @Schema(example = "INSTRUTOR")
    private String cargo;
}
