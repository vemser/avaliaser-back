package br.com.dbc.vemser.avaliaser.dto.usuario;

import br.com.dbc.vemser.avaliaser.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    private String email;
    private String nome;

}
