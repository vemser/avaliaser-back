package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.repositories.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
}
