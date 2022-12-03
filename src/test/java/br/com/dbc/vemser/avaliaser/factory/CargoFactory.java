package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.entities.CargoEntity;

public class CargoFactory {
    public static CargoEntity getCargo() {
        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(1);
        cargo.setNome("ROLE_ADMIN");
        return cargo;
    }
}
