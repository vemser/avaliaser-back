package br.com.dbc.vemser.avaliaser.service;

import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.factory.CargoFactory;
import br.com.dbc.vemser.avaliaser.repositories.avaliaser.CargoRepository;
import br.com.dbc.vemser.avaliaser.services.avaliaser.CargoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargosServiceTest {
    @InjectMocks
    private CargoService cargoService;
    @Mock
    private CargoRepository cargoRepository;

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer idCargo = 1;
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.empty());
        cargoService.findById(idCargo);

    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        CargoEntity cargo = CargoFactory.getCargo();
        Integer idCargo = 1;
        when(cargoRepository.findById(idCargo)).thenReturn(Optional.of(cargo));
        CargoEntity cargoEntity = cargoService.findById(idCargo);

        assertNotNull(cargoEntity);
    }
}
