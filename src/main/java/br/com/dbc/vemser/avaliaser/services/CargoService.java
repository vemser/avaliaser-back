package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.repositories.CargoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;

    public CargoEntity findById(Integer idCargo)  {
        return cargoRepository.findById(idCargo).get();
    }
}