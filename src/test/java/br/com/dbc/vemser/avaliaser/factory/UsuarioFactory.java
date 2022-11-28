package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.login.UsuarioCreateDTO;
import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;

public class UsuarioFactory {
    public static UsuarioEntity getUsuarioEntity(){
        UsuarioEntity usuario = new UsuarioEntity();
        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(1);
        cargo.setNome("ROLE_ADMIN");
        usuario.setIdUsuario(1);
        usuario.setNome("Paulo Sergio");
        usuario.setEmail("paulo.sergio@dbccompany.com");
        usuario.setSenha("123");
        usuario.setCargo(cargo);
        usuario.setAtivo(Ativo.S);
        return usuario;
    }

    public static UsuarioCreateDTO getUsuarioCreateDTO(){
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setNome("Paulo Sergio");
        usuarioCreateDTO.setEmail("paulo.sergio@dbccompany.com");
        return usuarioCreateDTO;
    }
}
