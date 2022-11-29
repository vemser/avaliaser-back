package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.repositories.AcompanhamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcompanhamentoService {

    private final AcompanhamentoRepository acompanhamentoRepository;
    

}
