package br.com.dbc.vemser.avaliaser.services.avaliaser;

import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;

    public CargoEntity findById(Integer idCargo) throws RegraDeNegocioException {
        return cargoRepository.findById(idCargo).orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado."));
    }
}
