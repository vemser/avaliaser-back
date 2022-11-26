package br.com.dbc.vemser.avaliaser.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    public UsernamePasswordAuthenticationToken isValid(String token) {
        return null;
    }
}
